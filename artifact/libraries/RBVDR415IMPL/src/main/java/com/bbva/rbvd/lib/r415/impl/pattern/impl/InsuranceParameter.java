package com.bbva.rbvd.lib.r415.impl.pattern.impl;

import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;
import com.bbva.pisd.dto.insurancedao.entities.PaymentPeriodEntity;
import com.bbva.pisd.lib.r226.PISDR226;
import com.bbva.pisd.lib.r601.PISDR601;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.preformalization.dao.QuotationDAO;
import com.bbva.rbvd.dto.preformalization.transfer.PayloadConfig;
import com.bbva.rbvd.dto.preformalization.util.ConstantsUtil;
import com.bbva.rbvd.dto.preformalization.util.RBVDMessageError;
import com.bbva.rbvd.lib.r415.impl.pattern.PreInsuranceProduct;
import com.bbva.rbvd.lib.r415.impl.service.dao.IQuotationDAO;
import com.bbva.rbvd.lib.r415.impl.service.dao.impl.QuotationDAOImpl;
import com.bbva.rbvd.lib.r415.impl.util.ValidationUtil;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class InsuranceParameter implements PreInsuranceProduct {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsuranceParameter.class);

    private final PISDR226 pisdr226;
    private final PISDR601 pisdr601;
    private final ApplicationConfigurationService applicationConfigurationService;

    public InsuranceParameter(PISDR226 pisdr226, PISDR601 pisdr601, ApplicationConfigurationService applicationConfigurationService) {
        this.pisdr226 = pisdr226;
        this.pisdr601 = pisdr601;
        this.applicationConfigurationService = applicationConfigurationService;
    }

    @Override
    public PayloadConfig getConfig(PolicyDTO input) {
        PayloadConfig payloadConfig = new PayloadConfig();

        validateIfQuotationExistInContract(input.getQuotationNumber());
        LOGGER.info("InsuranceParameter - getConfig() - quotation not exist in contract");

        evaluateIfPaymentIsRequired(input);

        QuotationDAO quotationDetail = getQuotationDetails(input.getQuotationNumber());
        LOGGER.info("InsuranceParameter - getConfig() - quotationDetail: {}",quotationDetail);

        PaymentPeriodEntity paymentPeriod = getPaymentPeriod(input.getInstallmentPlan().getPeriod().getId());
        LOGGER.info("InsuranceParameter - getConfig() - paymentPeriod: {}",paymentPeriod);

        payloadConfig.setQuotation(quotationDetail);
        payloadConfig.setPaymentFrequencyId(paymentPeriod.getPaymentFrequencyId());
        payloadConfig.setPaymentFrequencyName(payloadConfig.getPaymentFrequencyName());

        return payloadConfig;
    }

    private void validateIfQuotationExistInContract(String quotationId){
        boolean validateExist = this.pisdr226.executeFindQuotationIfExistInContract(quotationId);
        ValidationUtil.validateQuotationExistsInContract(validateExist);
    }

    private void evaluateIfPaymentIsRequired(PolicyDTO requestBody) {
        DateTimeZone dateTimeZone = DateTimeZone.forID(ConstantsUtil.TimeZone.LIMA_TIME_ZONE);

        DateTime currentLocalDate = new DateTime(new Date(), dateTimeZone);
        Date currentDate = currentLocalDate.toDate();

        dateTimeZone = DateTimeZone.forID(ConstantsUtil.TimeZone.GMT_TIME_ZONE);
        LocalDate startLocalDate = new LocalDate(requestBody.getValidityPeriod().getStartDate(), dateTimeZone);
        Date startDate = startLocalDate.toDateTimeAtStartOfDay().toDate();

        requestBody.getFirstInstallment().setIsPaymentRequired(!startDate.after(currentDate));
    }

    private QuotationDAO getQuotationDetails(String quotationNumber){
        IQuotationDAO quotationDAO = new QuotationDAOImpl(this.pisdr601);
        QuotationDAO quotationDetail = quotationDAO.getQuotationDetails(quotationNumber);

        LOGGER.info("InsuranceParameter - getQuotationDetails() - quotationDAO: {}", quotationDetail);

        ValidationUtil.validateObjectIsNull(quotationDetail,
                RBVDMessageError.QUOTATION_NOT_EXIST.getAdviceCode(),
                RBVDMessageError.QUOTATION_NOT_EXIST.getMessage());

        return quotationDetail;
    }

    private PaymentPeriodEntity getPaymentPeriod(String periodId){
        String frequencyType = this.applicationConfigurationService.getProperty(periodId);
        PaymentPeriodEntity paymentPeriod = this.pisdr226.executeFindPaymentPeriodByType(frequencyType);

        LOGGER.info("RBVDR415Impl - executeLogicPreFormalization() - paymentPeriod: {}", paymentPeriod);

        ValidationUtil.validateObjectIsNull(paymentPeriod, RBVDMessageError.PAYMENT_PERIOD_NOT_EXIST.getAdviceCode(), RBVDMessageError.PAYMENT_PERIOD_NOT_EXIST.getMessage());

        return paymentPeriod;
    }


    public static final class Builder{
        private PISDR226 pisdr226;
        private PISDR601 pisdr601;
        private ApplicationConfigurationService applicationConfigurationService;

        private Builder(){}

        public static Builder an() {
            return new Builder();
        }

        public Builder withPisdr226(PISDR226 pisdr226) {
            this.pisdr226 = pisdr226;
            return this;
        }

        public Builder withPisdr601(PISDR601 pisdr601) {
            this.pisdr601 = pisdr601;
            return this;
        }

        public Builder withApplicationConfigurationService(ApplicationConfigurationService applicationConfigurationService) {
            this.applicationConfigurationService = applicationConfigurationService;
            return this;
        }

        public InsuranceParameter build(){
            return new InsuranceParameter(pisdr226, pisdr601, applicationConfigurationService);
        }

    }

}
