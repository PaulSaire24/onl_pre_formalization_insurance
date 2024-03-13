package com.bbva.rbvd.rbvd118.impl.util;

import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICMRYS2;
import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Response;
import com.bbva.rbvd.dto.insrncsale.dao.InsuranceContractDAO;
import com.bbva.rbvd.dto.insrncsale.dao.RequiredFieldsEmissionDAO;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDProperties;
import com.bbva.rbvd.dto.preformalization.PreformalizationDTO;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class MapperHelper {
    private static final String GMT_TIME_ZONE = "GMT";
    private static final String S_VALUE = "S";
    private static final String N_VALUE = "N";
    private static final String PAYMENT_METHOD_VALUE = "DIRECT_DEBIT";
    ApplicationConfigurationService applicationConfigurationService;

    public Map<String, Object> createSingleArgument(String argument, String parameterName) {
        Map<String, Object> mapArgument = new HashMap<>();
        if (RBVDProperties.FIELD_POLICY_PAYMENT_FREQUENCY_TYPE.getValue().equals(parameterName)) {
            String frequencyType = applicationConfigurationService.getProperty(argument);
            mapArgument.put(parameterName, frequencyType);
            return mapArgument;
        }
        mapArgument.put(parameterName, argument);
        return mapArgument;
    }

    public InsuranceContractDAO buildInsuranceContract(PreformalizationDTO preformalizationBody, RequiredFieldsEmissionDAO emissionDao, ICR2Response icr2Response, Boolean isEndorsement) {
        InsuranceContractDAO contractDao = new InsuranceContractDAO();
        ICMRYS2 icmrys2 = icr2Response.getIcmrys2();
        String currentDate = generateCorrectDateFormat(LocalDate.now());

        contractDao.setEntityId(icmrys2.getNUMCON().substring(0, 4));
        contractDao.setBranchId(icmrys2.getNUMCON().substring(4, 8));
        contractDao.setIntAccountId(icmrys2.getNUMCON().substring(10));
        contractDao.setFirstVerfnDigitId(icmrys2.getNUMCON().substring(8, 9));
        contractDao.setSecondVerfnDigitId(icmrys2.getNUMCON().substring(9, 10));
        contractDao.setPolicyQuotaInternalId(preformalizationBody.getQuotationId());
        contractDao.setInsuranceProductId(emissionDao.getInsuranceProductId());
        contractDao.setInsuranceModalityType(preformalizationBody.getProduct().getPlan().getId());
        contractDao.setInsuranceCompanyId(new BigDecimal(preformalizationBody.getInsuranceCompany().getId()));
        contractDao.setCustomerId(preformalizationBody.getHolder().getId());
        contractDao.setDomicileContractId(preformalizationBody.getRelatedContracts().get(0).getContractId());
        contractDao.setIssuedReceiptNumber(BigDecimal.valueOf(preformalizationBody.getInstallmentPlan().getTotalNumberInstallments()));
        contractDao.setPaymentFrequencyId(emissionDao.getPaymentFrequencyId());
        contractDao.setPremiumAmount(BigDecimal.valueOf(preformalizationBody.getFirstInstallment().getPaymentAmount().getAmount()));
        contractDao.setSettlePendingPremiumAmount(BigDecimal.valueOf(preformalizationBody.getTotalAmount().getAmount()));
        contractDao.setCurrencyId(preformalizationBody.getInstallmentPlan().getPaymentAmount().getCurrency());
        contractDao.setInstallmentPeriodFinalDate(currentDate);
        contractDao.setInsuredAmount(BigDecimal.valueOf(preformalizationBody.getInsuredAmount().getAmount()));
        contractDao.setCtrctDisputeStatusType(preformalizationBody.getSaleChannelId());
        contractDao.setEndorsementPolicyIndType((isEndorsement) ? S_VALUE : N_VALUE);
        contractDao.setInsrncCoContractStatusType("ERR");
        contractDao.setCreationUserId(preformalizationBody.getCreationUser());
        contractDao.setUserAuditId(preformalizationBody.getUserAudit());
        contractDao.setInsurPendingDebtIndType((preformalizationBody.getFirstInstallment().getIsPaymentRequired()) ? N_VALUE : S_VALUE);
        contractDao.setContractManagerBranchId(preformalizationBody.getBank().getBranch().getId());
        contractDao.setContractInceptionDate(currentDate);
        contractDao.setSettlementFixPremiumAmount(BigDecimal.valueOf(preformalizationBody.getTotalAmount().getAmount()));
        contractDao.setBiometryTransactionId(preformalizationBody.getIdentityVerificationCode());

        if (nonNull(preformalizationBody.getBusinessAgent())) {
            contractDao.setInsuranceManagerId(preformalizationBody.getBusinessAgent().getId());
        }

        if (nonNull(preformalizationBody.getPromoter())) {
            contractDao.setInsurancePromoterId(preformalizationBody.getPromoter().getId());
        }

        contractDao.setEndLinkageDate(generateCorrectDateFormat(
                convertDateToLocalDate(preformalizationBody.getInstallmentPlan().getMaturityDate())));

        contractDao.setInsuranceContractStartDate(generateCorrectDateFormat(
                convertDateToLocalDate(preformalizationBody.getValidityPeriod().getStartDate())));

        contractDao.setValidityMonthsNumber(emissionDao.getContractDurationType().equals("A")
                ? emissionDao.getContractDurationNumber().multiply(BigDecimal.valueOf(12))
                : emissionDao.getContractDurationNumber());

        contractDao.setTotalDebtAmount((preformalizationBody.getFirstInstallment().getIsPaymentRequired())
                ? BigDecimal.ZERO : BigDecimal.valueOf(preformalizationBody.getFirstInstallment().getPaymentAmount().getAmount()));

        validatePrevPendBillRcptsNumber(preformalizationBody, emissionDao, contractDao);

        contractDao.setAutomaticDebitIndicatorType((preformalizationBody.getPaymentMethod().getPaymentType().equals(PAYMENT_METHOD_VALUE))
                ? S_VALUE : N_VALUE);

        return contractDao;
    }

    public void validatePrevPendBillRcptsNumber(PreformalizationDTO preformalizationBody, RequiredFieldsEmissionDAO emissionDao, InsuranceContractDAO contractDao) {
        BigDecimal prevPendBillRcptsNumber;
        String productId = preformalizationBody.getProductId();
        boolean isPaymentRequired = preformalizationBody.getFirstInstallment().getIsPaymentRequired();
        Long totalNumberInstallments = preformalizationBody.getInstallmentPlan().getTotalNumberInstallments();

        if (productId.equals(RBVDProperties.INSURANCE_PRODUCT_TYPE_VIDA_EASYYES.getValue())
                || productId.equals(RBVDProperties.INSURANCE_PRODUCT_TYPE_VIDA_2.getValue())) {
            prevPendBillRcptsNumber = emissionDao.getContractDurationNumber();
        } else {
            prevPendBillRcptsNumber = BigDecimal.valueOf(isPaymentRequired ? totalNumberInstallments - 1 : totalNumberInstallments);
        }

        contractDao.setPrevPendBillRcptsNumber(prevPendBillRcptsNumber);
    }

    public String generateCorrectDateFormat(LocalDate localDate) {
        return String.format("%02d/%02d/%d", localDate.getDayOfMonth(), localDate.getMonthOfYear(), localDate.getYear());
    }

    public LocalDate convertDateToLocalDate(Date date) {
        return new LocalDate(date, DateTimeZone.forID(GMT_TIME_ZONE));
    }

    public Map<String, Object> createSaveContractArguments(InsuranceContractDAO contractDao) {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put(RBVDProperties.FIELD_INSURANCE_CONTRACT_ENTITY_ID.getValue(), contractDao.getEntityId());
        arguments.put(RBVDProperties.FIELD_INSURANCE_CONTRACT_BRANCH_ID.getValue(), contractDao.getBranchId());
        arguments.put(RBVDProperties.FIELD_INSRC_CONTRACT_INT_ACCOUNT_ID.getValue(), contractDao.getIntAccountId());
        arguments.put(RBVDProperties.FIELD_CONTRACT_FIRST_VERFN_DIGIT_ID.getValue(), contractDao.getFirstVerfnDigitId());
        arguments.put(RBVDProperties.FIELD_CONTRACT_SECOND_VERFN_DIGIT_ID.getValue(), contractDao.getSecondVerfnDigitId());
        arguments.put(RBVDProperties.FIELD_POLICY_QUOTA_INTERNAL_ID.getValue(), contractDao.getPolicyQuotaInternalId());
        arguments.put(RBVDProperties.FIELD_INSURANCE_PRODUCT_ID.getValue(), contractDao.getInsuranceProductId());
        arguments.put(RBVDProperties.FIELD_INSURANCE_MODALITY_TYPE.getValue(), contractDao.getInsuranceModalityType());
        arguments.put(RBVDProperties.FIELD_INSURANCE_COMPANY_ID.getValue(), contractDao.getInsuranceCompanyId());
        arguments.put(RBVDProperties.FIELD_POLICY_ID.getValue(), contractDao.getPolicyId());
        arguments.put(RBVDProperties.FIELD_INSURANCE_MANAGER_ID.getValue(), contractDao.getInsuranceManagerId());
        arguments.put(RBVDProperties.FIELD_INSURANCE_PROMOTER_ID.getValue(), contractDao.getInsurancePromoterId());
        arguments.put(RBVDProperties.FIELD_CONTRACT_MANAGER_BRANCH_ID.getValue(), contractDao.getContractManagerBranchId());
        arguments.put(RBVDProperties.FIELD_CONTRACT_INCEPTION_DATE.getValue(), contractDao.getContractInceptionDate());
        arguments.put(RBVDProperties.FIELD_INSURANCE_CONTRACT_START_DATE.getValue(), contractDao.getInsuranceContractStartDate());
        arguments.put(RBVDProperties.FIELD_INSURANCE_CONTRACT_END_DATE.getValue(), contractDao.getInsuranceContractEndDate());
        arguments.put(RBVDProperties.FIELD_INSRNC_VALIDITY_MONTHS_NUMBER.getValue(), contractDao.getValidityMonthsNumber());
        arguments.put(RBVDProperties.FIELD_INSURANCE_POLICY_END_DATE.getValue(), contractDao.getInsurancePolicyEndDate());
        arguments.put(RBVDProperties.FIELD_CUSTOMER_ID.getValue(), contractDao.getCustomerId());
        arguments.put(RBVDProperties.FIELD_DOMICILE_CONTRACT_ID.getValue(), contractDao.getDomicileContractId());
        arguments.put(RBVDProperties.FIELD_CARD_ISSUING_MARK_TYPE.getValue(), contractDao.getCardIssuingMarkType());
        arguments.put(RBVDProperties.FIELD_ISSUED_RECEIPT_NUMBER.getValue(), contractDao.getIssuedReceiptNumber());
        arguments.put(RBVDProperties.FIELD_PAYMENT_FREQUENCY_ID.getValue(), contractDao.getPaymentFrequencyId());
        arguments.put(RBVDProperties.FIELD_PREMIUM_AMOUNT.getValue(), contractDao.getPremiumAmount());
        arguments.put(RBVDProperties.FIELD_SETTLE_PENDING_PREMIUM_AMOUNT.getValue(), contractDao.getSettlePendingPremiumAmount());
        arguments.put(RBVDProperties.FIELD_CURRENCY_ID.getValue(), contractDao.getCurrencyId());
        arguments.put(RBVDProperties.FIELD_LAST_INSTALLMENT_DATE.getValue(), contractDao.getLastInstallmentDate());
        arguments.put(RBVDProperties.FIELD_INSTALLMENT_PERIOD_FINAL_DATE.getValue(), contractDao.getInstallmentPeriodFinalDate());
        arguments.put(RBVDProperties.FIELD_INSURED_AMOUNT.getValue(), contractDao.getInsuredAmount());
        arguments.put(RBVDProperties.FIELD_BENEFICIARY_TYPE.getValue(), contractDao.getBeneficiaryType());
        arguments.put(RBVDProperties.FIELD_RENEWAL_NUMBER.getValue(), contractDao.getRenewalNumber());
        arguments.put(RBVDProperties.FIELD_CTRCT_DISPUTE_STATUS_TYPE.getValue(), contractDao.getCtrctDisputeStatusType());
        arguments.put(RBVDProperties.FIELD_PERIOD_NEXT_PAYMENT_DATE.getValue(), contractDao.getPeriodNextPaymentDate());
        arguments.put(RBVDProperties.FIELD_ENDORSEMENT_POLICY_IND_TYPE.getValue(), contractDao.getEndorsementPolicyIndType());
        arguments.put(RBVDProperties.FIELD_INSRNC_CO_CONTRACT_STATUS_TYPE.getValue(), contractDao.getInsrncCoContractStatusType());
        arguments.put(RBVDProperties.FIELD_CONTRACT_STATUS_ID.getValue(), contractDao.getContractStatusId());
        arguments.put(RBVDProperties.FIELD_CREATION_USER_ID.getValue(), contractDao.getCreationUserId());
        arguments.put(RBVDProperties.FIELD_USER_AUDIT_ID.getValue(), contractDao.getUserAuditId());
        arguments.put(RBVDProperties.FIELD_INSUR_PENDING_DEBT_IND_TYPE.getValue(), contractDao.getInsurPendingDebtIndType());
        arguments.put(RBVDProperties.FIELD_TOTAL_DEBT_AMOUNT.getValue(), contractDao.getTotalDebtAmount());
        arguments.put(RBVDProperties.FIELD_PREV_PEND_BILL_RCPTS_NUMBER.getValue(), contractDao.getPrevPendBillRcptsNumber());
        arguments.put(RBVDProperties.FIELD_SETTLEMENT_VAR_PREMIUM_AMOUNT.getValue(), contractDao.getSettlementVarPremiumAmount());
        arguments.put(RBVDProperties.FIELD_SETTLEMENT_FIX_PREMIUM_AMOUNT.getValue(), contractDao.getSettlementFixPremiumAmount());
        arguments.put(RBVDProperties.FIELD_INSURANCE_COMPANY_PRODUCT_ID.getValue(), contractDao.getInsuranceCompanyProductId());
        arguments.put(RBVDProperties.FIELD_AUTOMATIC_DEBIT_INDICATOR_TYPE.getValue(), contractDao.getAutomaticDebitIndicatorType());
        arguments.put(RBVDProperties.FIELD_BIOMETRY_TRANSACTION_ID.getValue(), contractDao.getBiometryTransactionId());
        arguments.put(RBVDProperties.FIELD_TELEMARKETING_TRANSACTION_ID.getValue(), contractDao.getTelemarketingTransactionId());
        arguments.put(RBVDProperties.FIELD_ORIGINAL_PAYMENT_SUBCHANNEL_ID.getValue(), contractDao.getOriginalPaymentSubchannelId());
        return arguments;
    }
}
