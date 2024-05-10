package com.bbva.rbvd.lib.rbvd118.impl;

import com.bbva.apx.exception.business.BusinessException;
import com.bbva.pisd.dto.insurancedao.entities.PaymentPeriodEntity;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Request;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Response;
import com.bbva.rbvd.dto.insrncsale.policy.BusinessAgentDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PromoterDTO;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDErrors;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDProperties;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDValidation;
import com.bbva.rbvd.dto.preformalization.dao.QuotationDAO;
import com.bbva.rbvd.dto.preformalization.util.ConstantsUtil;
import com.bbva.rbvd.lib.rbvd118.impl.service.dao.IContractDAO;
import com.bbva.rbvd.lib.rbvd118.impl.service.dao.IParticipantDAO;
import com.bbva.rbvd.lib.rbvd118.impl.service.dao.impl.ContractDAOImpl;
import com.bbva.rbvd.lib.rbvd118.impl.service.dao.impl.ParticipantDAOImpl;
import com.bbva.rbvd.lib.rbvd118.impl.transform.bean.QuotationBean;
import com.bbva.rbvd.lib.rbvd118.impl.util.ValidationUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

public class RBVDR118Impl extends RBVDR118Abstract {
    public static final Logger LOGGER = LoggerFactory.getLogger(RBVDR118Impl.class);


    public PolicyDTO executeLogicPreFormalization(PolicyDTO requestBody) {

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

        boolean isEndorsement = ValidationUtil.validateEndorsement(requestBody);

        //Inserta contrato
        IContractDAO contractDAO = new ContractDAOImpl(this.pisdr226);
        contractDAO.insertInsuranceContract(requestBody, quotationDAO, icr2Response, isEndorsement, paymentPeriod);

        IParticipantDAO participantDAO = new ParticipantDAOImpl(this.pisdR012);

        //Busca roles por producto y plan
        List<Map<String, Object>> rolesFromDB = participantDAO.getRolesByProductIdAndModality(
                quotationDAO.getInsuranceProductId(), requestBody.getProduct().getPlan().getId());

        if(!isEmpty(rolesFromDB) && !isEmpty(requestBody.getParticipants())){
           //Registra participantes
            participantDAO.insertInsuranceParticipants(requestBody, rolesFromDB, icr2Response.getIcmrys2().getNUMCON());
        }

        return requestBody;

    }


    public void validatePolicyExists(String quotation) {

        boolean validateExist = this.pisdr226.executeFindQuotationIfExistInContract(quotation);

        if (validateExist) {
            throw RBVDValidation.build(RBVDErrors.POLICY_ALREADY_EXISTS);
        }
    }

    public void evaluateIfPaymentIsRequired(PolicyDTO requestBody) {
        DateTimeZone dateTimeZone = DateTimeZone.forID(ConstantsUtil.RBVDR118.LIMA_TIME_ZONE);

        DateTime currentLocalDate = new DateTime(new Date(), dateTimeZone);
        Date currentDate = currentLocalDate.toDate();

        dateTimeZone = DateTimeZone.forID(ConstantsUtil.RBVDR118.GMT_TIME_ZONE);
        LocalDate startLocalDate = new LocalDate(requestBody.getValidityPeriod().getStartDate(), dateTimeZone);
        Date startDate = startLocalDate.toDateTimeAtStartOfDay().toDate();

        requestBody.getFirstInstallment().setIsPaymentRequired(!startDate.after(currentDate));
    }


    public void setSaleChannelIdFromBranchId(PolicyDTO requestBody, String branchId) {
        String tlmktValue = this.applicationConfigurationService.getProperty(ConstantsUtil.ApxConsole.KEY_TLMKT_CODE);
        if (tlmktValue.equals(branchId)) {
            LOGGER.info("***** RBVDR118Impl - setSaleChannelIdFromBranchId | It's TLMKT Channel *****");
            requestBody.setSaleChannelId("TM");
        }
    }

}