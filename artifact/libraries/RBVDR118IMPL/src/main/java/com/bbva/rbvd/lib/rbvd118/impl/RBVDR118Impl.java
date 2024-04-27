package com.bbva.rbvd.lib.rbvd118.impl;

import com.bbva.apx.exception.business.BusinessException;
import com.bbva.pisd.dto.insurance.utils.PISDProperties;
import com.bbva.pisd.dto.insurancedao.entities.PaymentPeriodEntity;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Request;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Response;
import com.bbva.rbvd.dto.insrncsale.dao.InsuranceContractDAO;
import com.bbva.rbvd.dto.insrncsale.policy.BusinessAgentDTO;
import com.bbva.rbvd.dto.insrncsale.policy.ParticipantDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PromoterDTO;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDErrors;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDProperties;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDValidation;
import com.bbva.rbvd.dto.preformalization.dao.QuotationDAO;
import com.bbva.rbvd.dto.preformalization.dto.InsuranceDTO;
import com.bbva.rbvd.lib.rbvd118.impl.transform.bean.QuotationBean;
import com.bbva.rbvd.lib.rbvd118.impl.transform.map.ContractMap;
import com.bbva.rbvd.lib.rbvd118.impl.util.ConstantsUtil;
import com.bbva.rbvd.lib.rbvd118.impl.util.ValidationUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

public class RBVDR118Impl extends RBVDR118Abstract {
    public static final Logger LOGGER = LoggerFactory.getLogger(RBVDR118Impl.class);


    public InsuranceDTO executeLogicPreFormalization(InsuranceDTO requestBody) {
        try {
            LOGGER.info("RBVDR118Impl - executeLogicPreFormalization() - Params: {}", requestBody);

            validatePolicyExists(requestBody.getQuotationNumber());

            evaluateIfPaymentIsRequired(requestBody);

            Map<String, Object> quotationIdArgument = Collections.singletonMap(
                    RBVDProperties.FIELD_POLICY_QUOTA_INTERNAL_ID.getValue(),requestBody.getQuotationNumber());


            //DEVUELVE MUCHOS CAMPOS, REEMPLAZAR POR OTRO QUERY. Usar lib_dao_insurance_quotation
            Map<String, Object> contractRequiredFields = pisdR012.executeGetASingleRow(
                    RBVDProperties.DYNAMIC_QUERY_FOR_INSURANCE_CONTRACT.getValue(), quotationIdArgument);

            PaymentPeriodEntity paymentPeriod = this.pisdr226.executeFindPaymentPeriodByType(
                    requestBody.getInstallmentPlan().getPeriod().getId());

            if (isEmpty(contractRequiredFields)) {
                throw RBVDValidation.build(RBVDErrors.NON_EXISTENT_QUOTATION);
            }

            QuotationDAO quotationDAO = QuotationBean.transformQuotationMapToBean(contractRequiredFields);

            ICR2Request icr2Request = icr2Business.mapRequestFromPreformalizationBody(requestBody);
            ICR2Response icr2Response = rbvdR047.executePolicyRegistration(icr2Request);

            String hostBranchId = icr2Response.getIcmrys2().getOFICON();
            requestBody.getBank().getBranch().setId(hostBranchId);

            setSaleChannelIdFromBranchId(requestBody, hostBranchId);

            validateDigitalSale(requestBody);

            Boolean isEndorsement = validateEndorsement(requestBody);

            InsuranceContractDAO contractDao = mapperHelper.buildInsuranceContract(requestBody, quotationDAO,
                    icr2Response, isEndorsement, paymentPeriod);

            Map<String, Object> argumentsForSaveContract = ContractMap.createSaveContractArguments(contractDao);

            int insertContract = this.pisdr226.executeInsertInsuranceContract(argumentsForSaveContract);

            validateInsertion(insertContract, RBVDErrors.INSERTION_ERROR_IN_CONTRACT_TABLE);

            //Agregar lógica para insertar participants
            //Guiarse la lógica de cómo lo hace en la lib interna de la trx RBVDT201

            return requestBody;
        } catch (BusinessException e) {
            addAdviceWithDescription(e.getAdviceCode(), e.getMessage());
            return null;
        }
    }

    //Mover a una clase ValidationUtil
    public void validatePolicyExists(String quotation) {

        boolean validateExist = this.pisdr226.executeFindQuotationIfExistInContract(quotation);

        if (validateExist) {
            throw RBVDValidation.build(RBVDErrors.POLICY_ALREADY_EXISTS);
        }
    }

    //Mover a una clase ValidationUtil
    public void evaluateIfPaymentIsRequired(InsuranceDTO requestBody) {
        DateTimeZone dateTimeZone = DateTimeZone.forID(ConstantsUtil.RBVDR118.LIMA_TIME_ZONE);

        DateTime currentLocalDate = new DateTime(new Date(), dateTimeZone);
        Date currentDate = currentLocalDate.toDate();

        dateTimeZone = DateTimeZone.forID(ConstantsUtil.RBVDR118.GMT_TIME_ZONE);
        LocalDate startLocalDate = new LocalDate(requestBody.getInsuranceValidityPeriod().getStartDate(), dateTimeZone);
        Date startDate = startLocalDate.toDateTimeAtStartOfDay().toDate();

        requestBody.getFirstInstallment().setIsPaymentRequired(!startDate.after(currentDate));
    }


    //Mover a una clase ValidationUtil
    public void setSaleChannelIdFromBranchId(InsuranceDTO requestBody, String branchId) {
        String tlmktValue = this.applicationConfigurationService.getProperty(ConstantsUtil.ApxConsole.KEY_TLMKT_CODE);
        if (tlmktValue.equals(branchId)) {
            LOGGER.info("***** RBVDR211Impl - executeBusinessLogicEmissionPrePolicy | It's TLMKT Channel *****");
            requestBody.setSaleChannelId("TM");
        }
    }


    //Mover a una clase ValidationUtil
    public void validateDigitalSale(InsuranceDTO requestBody) {
        String saleChannelId = requestBody.getSaleChannelId();
        String picCodeValue = this.applicationConfigurationService.getProperty(ConstantsUtil.ApxConsole.KEY_PIC_CODE);
        String contactCenterCode = this.applicationConfigurationService.getProperty(ConstantsUtil.ApxConsole.KEY_CONTACT_CENTER_CODE);
        String telemarketingCode = "TM";

        if (picCodeValue.equals(saleChannelId) || !telemarketingCode.equals(saleChannelId)
                || !saleChannelId.equals(contactCenterCode))
            return;

        LOGGER.info("***** It's digital sale!! *****");
        String appGlomo = this.applicationConfigurationService.getProperty(ConstantsUtil.ApxConsole.CHANNEL_GLOMO);
        String appContactDetail = this.applicationConfigurationService.getProperty(ConstantsUtil.ApxConsole.CHANNEL_CONTACT_DETAIL);
        String[] appSearchContactDetail = StringUtils.defaultIfBlank(appContactDetail, "").split(";");

        if (appGlomo.equalsIgnoreCase(requestBody.getAap())
                || Arrays.asList(appSearchContactDetail).contains(requestBody.getAap())) {
            //TODO: Implementar canal de venta digital - Glomo
            throw new BusinessException("RBVD00000136", true, "Canal de venta digital no implementado");
        }

        String defaultCode = this.applicationConfigurationService.getProperty(ConstantsUtil.ApxConsole.KEY_AGENT_PROMOTER_CODE);

        BusinessAgentDTO businessAgent = new BusinessAgentDTO();
        businessAgent.setId(defaultCode);

        if (isNull(requestBody.getPromoter())) {
            PromoterDTO promoter = new PromoterDTO();
            promoter.setId(defaultCode);
            requestBody.setPromoter(promoter);
        }
        requestBody.setBusinessAgent(businessAgent);
    }


    //Mover a una clase ValidationUtil
    public boolean validateEndorsement(InsuranceDTO requestBody) {

        ParticipantDTO participantDTO = ValidationUtil.filterParticipantByType(
                requestBody.getParticipants(), ConstantsUtil.Participant.ENDORSEE);

        return participantDTO != null && participantDTO.getIdentityDocument() != null
                && participantDTO.getIdentityDocument().getDocumentType().getId() != null
                && ConstantsUtil.DocumentType.RUC.equals(participantDTO.getIdentityDocument().getDocumentType().getId())
                && participantDTO.getBenefitPercentage() != null;
    }


    //Mover a una clase ValidationUtil
    public void validateInsertion(int insertedRows, RBVDErrors error) {
        if (insertedRows != 1) {
            throw RBVDValidation.build(error);
        }
    }
}