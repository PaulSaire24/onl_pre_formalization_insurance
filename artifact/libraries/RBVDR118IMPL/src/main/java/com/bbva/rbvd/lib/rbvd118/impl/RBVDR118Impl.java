package com.bbva.rbvd.lib.rbvd118.impl;

import com.bbva.apx.exception.business.BusinessException;
import com.bbva.pisd.dto.insurance.utils.PISDProperties;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Request;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Response;
import com.bbva.rbvd.dto.insrncsale.dao.InsuranceContractDAO;
import com.bbva.rbvd.dto.insrncsale.dao.RequiredFieldsEmissionDAO;
import com.bbva.rbvd.dto.insrncsale.policy.BusinessAgentDTO;
import com.bbva.rbvd.dto.insrncsale.policy.ParticipantDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PromoterDTO;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDErrors;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDProperties;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDValidation;
import com.bbva.rbvd.dto.preformalization.PreformalizationDTO;
import com.bbva.rbvd.lib.rbvd118.impl.util.ConstantsUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class RBVDR118Impl extends RBVDR118Abstract {
    public static final Logger LOGGER = LoggerFactory.getLogger(RBVDR118Impl.class);
    public static final String KEY_TLMKT_CODE = "telemarketing.code";
    public static final String KEY_PIC_CODE = "pic.code";
    public static final String KEY_CONTACT_CENTER_CODE = "cc.code";
    public static final String KEY_AGENT_PROMOTER_CODE = "agent.and.promoter.code";
    public static final String KEY_CYPHER_CODE = "apx-pe-fpextff1-do";
    public static final String CHANNEL_GLOMO = "pisd.channel.glomo.aap";
    public static final String CHANNEL_CONTACT_DETAIL = "pisd.channel.contact.detail.aap";
    public static final String TAG_ENDORSEE = "ENDORSEE";
    public static final String TAG_RUC = "RUC";

    public PreformalizationDTO executePreFormalization(PreformalizationDTO requestBody) {
        try {
            LOGGER.info("executePreFormalization - Start with values: {}", requestBody);

            validatePolicyExists(requestBody);

            Map<String, Object> quotationIdArgument = mapperHelper.createSingleArgument(requestBody.getQuotationNumber(), RBVDProperties.FIELD_POLICY_QUOTA_INTERNAL_ID.getValue());
            Map<String, Object> frequencyTypeArgument = mapperHelper.createSingleArgument(requestBody.getInstallmentPlan().getPeriod().getId(), RBVDProperties.FIELD_POLICY_PAYMENT_FREQUENCY_TYPE.getValue());

            Map<String, Object> contractRequiredFields = pisdR012.executeGetASingleRow(RBVDProperties.DYNAMIC_QUERY_FOR_INSURANCE_CONTRACT.getValue(), quotationIdArgument);
            Map<String, Object> paymentPeriodResponse = pisdR012.executeGetASingleRow(RBVDProperties.QUERY_SELECT_PAYMENT_PERIOD.getValue(), frequencyTypeArgument);
            Map<String, Object> productResponse = (Map<String, Object>) pisdR401.executeGetProductById(ConstantsUtil.Queries.QUERY_SELECT_PRODUCT_BY_PRODUCT_TYPE, singletonMap(RBVDProperties.FIELD_INSURANCE_PRODUCT_TYPE.getValue(), requestBody.getProduct().getId()));

            RequiredFieldsEmissionDAO emissionDao = validationUtil.validateResponseQueryGetRequiredFields(contractRequiredFields, paymentPeriodResponse);

            evaluateIfPaymentIsRequired(requestBody);

            ICR2Request icr2Request = icr2Helper.mapRequestFromPreformalizationBody(requestBody);

            ICR2Response icr2Response = rbvdR047.executePolicyRegistration(icr2Request);

            String hostBranchId = icr2Response.getIcmrys2().getOFICON();
            requestBody.getBank().getBranch().setId(hostBranchId);

            setSaleChannelIdFromBranchId(requestBody, hostBranchId);

            validateDigitalSale(requestBody);

            Boolean isEndorsement = validateEndorsement(requestBody);

            InsuranceContractDAO contractDao = mapperHelper.buildInsuranceContract(requestBody, emissionDao, icr2Response, isEndorsement);

            Map<String, Object> argumentsForSaveContract = mapperHelper.createSaveContractArguments(contractDao);

            int insertedContract = pisdR012.executeInsertSingleRow(PISDProperties.QUERY_INSERT_INSURANCE_CONTRACT.getValue(),
                    argumentsForSaveContract,
                    RBVDProperties.FIELD_INSURANCE_CONTRACT_ENTITY_ID.getValue(),
                    RBVDProperties.FIELD_INSURANCE_CONTRACT_BRANCH_ID.getValue(),
                    RBVDProperties.FIELD_INSURANCE_PRODUCT_ID.getValue(),
                    RBVDProperties.FIELD_INSURANCE_MODALITY_TYPE.getValue(),
                    RBVDProperties.FIELD_INSURANCE_COMPANY_ID.getValue(),
                    RBVDProperties.FIELD_INSURANCE_CONTRACT_START_DATE.getValue(),
                    RBVDProperties.FIELD_CUSTOMER_ID.getValue(),
                    RBVDProperties.FIELD_INSRNC_CO_CONTRACT_STATUS_TYPE.getValue(),
                    RBVDProperties.FIELD_INSRC_CONTRACT_INT_ACCOUNT_ID.getValue(),
                    RBVDProperties.FIELD_USER_AUDIT_ID.getValue());

            validateInsertion(insertedContract, RBVDErrors.INSERTION_ERROR_IN_CONTRACT_TABLE);

            return requestBody;
        } catch (BusinessException e) {
            addAdviceWithDescription(e.getAdviceCode(), e.getMessage());
            return null;
        }
    }

    public void validatePolicyExists(PreformalizationDTO preformalizationDTO) {
        Map<String, Object> quotationIdArgument;
        Map<String, Object> responseValidateIfPolicyExists;
        BigDecimal resultNumber;

        quotationIdArgument = this.mapperHelper.createSingleArgument(preformalizationDTO.getQuotationNumber(),
                RBVDProperties.FIELD_POLICY_QUOTA_INTERNAL_ID.getValue());

        responseValidateIfPolicyExists = pisdR012.executeGetASingleRow(RBVDProperties.QUERY_VALIDATE_IF_POLICY_EXISTS.getValue(), quotationIdArgument);

        resultNumber = (BigDecimal) responseValidateIfPolicyExists.get(RBVDProperties.FIELD_RESULT_NUMBER.getValue());

        if (nonNull(resultNumber) && resultNumber.compareTo(BigDecimal.ONE) == 0) {
            throw RBVDValidation.build(RBVDErrors.POLICY_ALREADY_EXISTS);
        }
    }

    public void evaluateIfPaymentIsRequired(PreformalizationDTO requestBody) {
        DateTimeZone dateTimeZone = DateTimeZone.forID(ConstantsUtil.RBVDR118.LIMA_TIME_ZONE);

        DateTime currentLocalDate = new DateTime(new Date(), dateTimeZone);
        Date currentDate = currentLocalDate.toDate();

        dateTimeZone = DateTimeZone.forID(ConstantsUtil.RBVDR118.GMT_TIME_ZONE);
        LocalDate startLocalDate = new LocalDate(requestBody.getInsuranceValidityPeriod().getStartDate(), dateTimeZone);
        Date startDate = startLocalDate.toDateTimeAtStartOfDay().toDate();

        requestBody.getFirstInstallment().setIsPaymentRequired(!startDate.after(currentDate));
    }

    public void setSaleChannelIdFromBranchId(PreformalizationDTO requestBody, String branchId) {
        String tlmktValue = this.applicationConfigurationService.getProperty(KEY_TLMKT_CODE);
        if (tlmktValue.equals(branchId)) {
            LOGGER.info("***** RBVDR211Impl - executeBusinessLogicEmissionPrePolicy | It's TLMKT Channel *****");
            requestBody.setSaleChannelId("TM");
        }
    }

    public void validateDigitalSale(PreformalizationDTO requestBody) {
        String saleChannelId = requestBody.getSaleChannelId();
        String picCodeValue = this.applicationConfigurationService.getProperty(KEY_PIC_CODE);
        String contactCenterCode = this.applicationConfigurationService.getProperty(KEY_CONTACT_CENTER_CODE);
        String telemarketingCode = "TM";

        if (picCodeValue.equals(saleChannelId) || !telemarketingCode.equals(saleChannelId) || !saleChannelId.equals(contactCenterCode))
            return;

        LOGGER.info("***** It's digital sale!! *****");
        String appGlomo = this.applicationConfigurationService.getProperty(CHANNEL_GLOMO);
        String appContactDetail = this.applicationConfigurationService.getProperty(CHANNEL_CONTACT_DETAIL);
        String[] appSearchContactDetail = StringUtils.defaultIfBlank(appContactDetail, "").split(";");

        if (appGlomo.equalsIgnoreCase(requestBody.getAap()) || Arrays.asList(appSearchContactDetail).contains(requestBody.getAap())) {
            //TODO: Implementar canal de venta digital - Glomo
            throw new BusinessException("RBVD00000136", true, "Canal de venta digital no implementado");
        }

        String defaultCode = this.applicationConfigurationService.getProperty(KEY_AGENT_PROMOTER_CODE);

        BusinessAgentDTO businessAgent = new BusinessAgentDTO();
        businessAgent.setId(defaultCode);

        if (isNull(requestBody.getPromoter())) {
            PromoterDTO promoter = new PromoterDTO();
            promoter.setId(defaultCode);
            requestBody.setPromoter(promoter);
        }
        requestBody.setBusinessAgent(businessAgent);
    }

    public boolean validateEndorsement(PreformalizationDTO requestBody) {
        if (requestBody.getParticipants() == null || requestBody.getParticipants().size() <= ConstantsUtil.Number.UNO) {
            return false;
        }

        ParticipantDTO participant = requestBody.getParticipants().get(ConstantsUtil.Number.UNO);
        if (participant.getIdentityDocument() == null || participant.getBenefitPercentage() == null) {
            return false;
        }

        return TAG_ENDORSEE.equals(participant.getParticipantType().getId())
                && TAG_RUC.equals(participant.getIdentityDocument().getDocumentType().getId());
    }

    public void validateInsertion(int insertedRows, RBVDErrors error) {
        if (insertedRows != 1) {
            throw RBVDValidation.build(error);
        }
    }
}