package com.bbva.rbvd.lib.r118.impl;

import com.bbva.apx.exception.business.BusinessException;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Request;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Response;
import com.bbva.rbvd.dto.insrncsale.dao.RequiredFieldsEmissionDAO;
import com.bbva.rbvd.dto.insrncsale.policy.BusinessAgentDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PromoterDTO;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDErrors;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDProperties;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDValidation;
import com.bbva.rbvd.dto.preformalization.PreformalizationDTO;
import com.bbva.rbvd.lib.r118.impl.util.ConstantsUtil;
import com.bbva.rbvd.lib.r118.impl.util.ValidationUtil;
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

import static com.bbva.rbvd.lib.r118.impl.util.ConstantsUtil.RBVDR118.GMT_TIME_ZONE;
import static com.bbva.rbvd.lib.r118.impl.util.ConstantsUtil.RBVDR118.LIMA_TIME_ZONE;
import static java.util.Collections.singletonMap;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class RBVDR118Impl extends RBVDR118Abstract {
    private static final Logger LOGGER = LoggerFactory.getLogger(RBVDR118Impl.class);
    private static final String KEY_TLMKT_CODE = "telemarketing.code";
    private static final String KEY_PIC_CODE = "pic.code";
    private static final String KEY_CONTACT_CENTER_CODE = "cc.code";
    private static final String KEY_AGENT_PROMOTER_CODE = "agent.and.promoter.code";
    private static final String KEY_CYPHER_CODE = "apx-pe-fpextff1-do";
    private static final String CHANNEL_GLOMO = "pisd.channel.glomo.aap";
    private static final String CHANNEL_CONTACT_DETAIL = "pisd.channel.contact.detail.aap";

    @Override
    public PreformalizationDTO executePreFormalization(PreformalizationDTO requestBody) {

        try {
            LOGGER.info("executePreFormalization - Start with values: {}", requestBody);

            LOGGER.info("executePreFormalization - Validating if policy exists");
            validatePolicyExists(requestBody);

            Map<String, Object> quotationIdArgument = this.mapperHelper.createSingleArgument(requestBody.getQuotationNumber(),
                    RBVDProperties.FIELD_POLICY_QUOTA_INTERNAL_ID.getValue());

            Map<String, Object> frequencyTypeArgument = this.mapperHelper.createSingleArgument(requestBody.getInstallmentPlan().getPeriod().getId(),
                    RBVDProperties.FIELD_POLICY_PAYMENT_FREQUENCY_TYPE.getValue());

            Map<String, Object> contractRequiredFields = pisdR012.executeGetASingleRow(RBVDProperties.DYNAMIC_QUERY_FOR_INSURANCE_CONTRACT.getValue(),
                    quotationIdArgument);

            Map<String, Object> paymentPeriodResponse = pisdR012.executeGetASingleRow(RBVDProperties.QUERY_SELECT_PAYMENT_PERIOD.getValue(),
                    frequencyTypeArgument);

            Map<String, Object> productResponse = (Map<String, Object>) this.pisdR401.executeGetProductById(ConstantsUtil.Queries.QUERY_SELECT_PRODUCT_BY_PRODUCT_TYPE,
                    singletonMap(RBVDProperties.FIELD_INSURANCE_PRODUCT_TYPE.getValue(), requestBody.getProduct().getId()));

            RequiredFieldsEmissionDAO emissionDao = ValidationUtil.validateResponseQueryGetRequiredFields(contractRequiredFields, paymentPeriodResponse);

            LOGGER.info("executePreFormalization - Evaluate if payment is required");
            this.evaluateIfPaymentIsRequired(requestBody);

            LOGGER.info("executePreFormalization - Mapping request to ICR2Request");
            ICR2Request icr2Request = icr2Helper.mapRequestFromPreformalizationBody(requestBody);

            LOGGER.info("executePreFormalization - Calling RBVDR047 to executePolicyRegistration with values: {}", icr2Request);
            ICR2Response icr2Response = rbvdR047.executePolicyRegistration(icr2Request);
            LOGGER.info("executePreFormalization - Response from RBVDR047: {}", icr2Response);

            String hostBranchId = icr2Response.getIcmrys2().getOFICON();
            requestBody.getBank().getBranch().setId(hostBranchId);
            LOGGER.info("executePreFormalization - Host branch id: {}", hostBranchId);

            LOGGER.info("executePreFormalization - Setting saleChannelId depending on branchId: {}", hostBranchId);
            this.setSaleChannelIdFromBranchId(requestBody, hostBranchId);

            LOGGER.info("executePreFormalization - Validating digital sale");
            validateDigitalSale(requestBody);

            return requestBody;
        } catch (BusinessException e) {
            this.addAdviceWithDescription(e.getAdviceCode(), e.getMessage());
            return null;
        }
    }

    protected void validatePolicyExists(PreformalizationDTO preformalizationDTO) {
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

    private void evaluateIfPaymentIsRequired(PreformalizationDTO requestBody) {
        DateTimeZone dateTimeZone = DateTimeZone.forID(LIMA_TIME_ZONE);

        DateTime currentLocalDate = new DateTime(new Date(), dateTimeZone);
        Date currentDate = currentLocalDate.toDate();

        dateTimeZone = DateTimeZone.forID(GMT_TIME_ZONE);
        LocalDate startLocalDate = new LocalDate(requestBody.getValidityPeriod().getStartDate(), dateTimeZone);
        Date startDate = startLocalDate.toDateTimeAtStartOfDay().toDate();

        requestBody.getFirstInstallment().setIsPaymentRequired(!startDate.after(currentDate));
    }

    private void setSaleChannelIdFromBranchId(PreformalizationDTO requestBody, String branchId) {
        String tlmktValue = this.applicationConfigurationService.getProperty(KEY_TLMKT_CODE);
        if (tlmktValue.equals(branchId)) {
            LOGGER.info("***** RBVDR211Impl - executeBusinessLogicEmissionPrePolicy | It's TLMKT Channel *****");
            requestBody.setSaleChannelId("TM");
        }
    }

    private void validateDigitalSale(PreformalizationDTO requestBody) {
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
}
