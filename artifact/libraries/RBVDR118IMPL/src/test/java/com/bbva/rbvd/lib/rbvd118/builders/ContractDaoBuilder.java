package com.bbva.rbvd.lib.rbvd118.builders;

import com.bbva.rbvd.dto.insrncsale.dao.InsuranceContractDAO;

import java.math.BigDecimal;

public class ContractDaoBuilder {
    private final InsuranceContractDAO contractDao;

    public ContractDaoBuilder() {
        contractDao = new InsuranceContractDAO();
    }

    public static ContractDaoBuilder instance() {
        return new ContractDaoBuilder();
    }

    public ContractDaoBuilder withEntityId(String entityId) {
        contractDao.setEntityId(entityId);
        return this;
    }

    public ContractDaoBuilder withBranchId(String branchId) {
        contractDao.setBranchId(branchId);
        return this;
    }

    public ContractDaoBuilder withIntAccountId(String intAccountId) {
        contractDao.setIntAccountId(intAccountId);
        return this;
    }

    public ContractDaoBuilder withFirstVerfnDigitId(String firstVerfnDigitId) {
        contractDao.setFirstVerfnDigitId(firstVerfnDigitId);
        return this;
    }

    public ContractDaoBuilder withSecondVerfnDigitId(String secondVerfnDigitId) {
        contractDao.setSecondVerfnDigitId(secondVerfnDigitId);
        return this;
    }

    public ContractDaoBuilder withPolicyQuotaInternalId(String policyQuotaInternalId) {
        contractDao.setPolicyQuotaInternalId(policyQuotaInternalId);
        return this;
    }

    public ContractDaoBuilder withInsuranceProductId(BigDecimal insuranceProductId) {
        contractDao.setInsuranceProductId(insuranceProductId);
        return this;
    }

    public ContractDaoBuilder withInsuranceModalityType(String insuranceModalityType) {
        contractDao.setInsuranceModalityType(insuranceModalityType);
        return this;
    }

    public ContractDaoBuilder withInsuranceCompanyId(BigDecimal insuranceCompanyId) {
        contractDao.setInsuranceCompanyId(insuranceCompanyId);
        return this;
    }

    public ContractDaoBuilder withPolicyId(String policyId) {
        contractDao.setPolicyId(policyId);
        return this;
    }

    public ContractDaoBuilder withInsuranceManagerId(String insuranceManagerId) {
        contractDao.setInsuranceManagerId(insuranceManagerId);
        return this;
    }

    public ContractDaoBuilder withInsurancePromoterId(String insurancePromoterId) {
        contractDao.setInsurancePromoterId(insurancePromoterId);
        return this;
    }

    public ContractDaoBuilder withContractManagerBranchId(String contractManagerBranchId) {
        contractDao.setContractManagerBranchId(contractManagerBranchId);
        return this;
    }

    public ContractDaoBuilder withContractInceptionDate(String contractInceptionDate) {
        contractDao.setContractInceptionDate(contractInceptionDate);
        return this;
    }

    public ContractDaoBuilder withInsuranceContractStartDate(String insuranceContractStartDate) {
        contractDao.setInsuranceContractStartDate(insuranceContractStartDate);
        return this;
    }

    public ContractDaoBuilder withInsuranceContractEndDate(String insuranceContractEndDate) {
        contractDao.setInsuranceContractEndDate(insuranceContractEndDate);
        return this;
    }

    public ContractDaoBuilder withValidityMonthsNumber(BigDecimal validityMonthsNumber) {
        contractDao.setValidityMonthsNumber(validityMonthsNumber);
        return this;
    }

    public ContractDaoBuilder withInsurancePolicyEndDate(String insurancePolicyEndDate) {
        contractDao.setInsurancePolicyEndDate(insurancePolicyEndDate);
        return this;
    }

    public ContractDaoBuilder withCustomerId(String customerId) {
        contractDao.setCustomerId(customerId);
        return this;
    }

    public ContractDaoBuilder withDomicileContractId(String domicileContractId) {
        contractDao.setDomicileContractId(domicileContractId);
        return this;
    }

    public ContractDaoBuilder withCardIssuingMarkType(String cardIssuingMarkType) {
        contractDao.setCardIssuingMarkType(cardIssuingMarkType);
        return this;
    }

    public ContractDaoBuilder withIssuedReceiptNumber(BigDecimal issuedReceiptNumber) {
        contractDao.setIssuedReceiptNumber(issuedReceiptNumber);
        return this;
    }

    public ContractDaoBuilder withPaymentFrequencyId(BigDecimal paymentFrequencyId) {
        contractDao.setPaymentFrequencyId(paymentFrequencyId);
        return this;
    }

    public ContractDaoBuilder withPremiumAmount(BigDecimal premiumAmount) {
        contractDao.setPremiumAmount(premiumAmount);
        return this;
    }

    public ContractDaoBuilder withSettlePendingPremiumAmount(BigDecimal settlePendingPremiumAmount) {
        contractDao.setSettlePendingPremiumAmount(settlePendingPremiumAmount);
        return this;
    }

    public ContractDaoBuilder withCurrencyId(String currencyId) {
        contractDao.setCurrencyId(currencyId);
        return this;
    }

    public ContractDaoBuilder withLastInstallmentDate(String lastInstallmentDate) {
        contractDao.setLastInstallmentDate(lastInstallmentDate);
        return this;
    }

    public ContractDaoBuilder withInstallmentPeriodFinalDate(String installmentPeriodFinalDate) {
        contractDao.setInstallmentPeriodFinalDate(installmentPeriodFinalDate);
        return this;
    }

    public ContractDaoBuilder withInsuredAmount(BigDecimal insuredAmount) {
        contractDao.setInsuredAmount(insuredAmount);
        return this;
    }

    public ContractDaoBuilder withBeneficiaryType(String beneficiaryType) {
        contractDao.setBeneficiaryType(beneficiaryType);
        return this;
    }

    public ContractDaoBuilder withRenewalNumber(BigDecimal renewalNumber) {
        contractDao.setRenewalNumber(renewalNumber);
        return this;
    }

    public ContractDaoBuilder withCtrctDisputeStatusType(String ctrctDisputeStatusType) {
        contractDao.setCtrctDisputeStatusType(ctrctDisputeStatusType);
        return this;
    }

    public ContractDaoBuilder withPeriodNextPaymentDate(String periodNextPaymentDate) {
        contractDao.setPeriodNextPaymentDate(periodNextPaymentDate);
        return this;
    }

    public ContractDaoBuilder withEndorsementPolicyIndType(String endorsementPolicyIndType) {
        contractDao.setEndorsementPolicyIndType(endorsementPolicyIndType);
        return this;
    }

    public ContractDaoBuilder withInsrncCoContractStatusType(String insrncCoContractStatusType) {
        contractDao.setInsrncCoContractStatusType(insrncCoContractStatusType);
        return this;
    }

    public ContractDaoBuilder withContractStatusId(String contractStatusId) {
        contractDao.setContractStatusId(contractStatusId);
        return this;
    }

    public ContractDaoBuilder withCreationUserId(String creationUserId) {
        contractDao.setCreationUserId(creationUserId);
        return this;
    }

    public ContractDaoBuilder withUserAuditId(String userAuditId) {
        contractDao.setUserAuditId(userAuditId);
        return this;
    }

    public ContractDaoBuilder withInsurPendingDebtIndType(String insurPendingDebtIndType) {
        contractDao.setInsurPendingDebtIndType(insurPendingDebtIndType);
        return this;
    }

    public ContractDaoBuilder withTotalDebtAmount(BigDecimal totalDebtAmount) {
        contractDao.setTotalDebtAmount(totalDebtAmount);
        return this;
    }

    public ContractDaoBuilder withPrevPendBillRcptsNumber(BigDecimal prevPendBillRcptsNumber) {
        contractDao.setPrevPendBillRcptsNumber(prevPendBillRcptsNumber);
        return this;
    }

    public ContractDaoBuilder withSettlementVarPremiumAmount(BigDecimal settlementVarPremiumAmount) {
        contractDao.setSettlementVarPremiumAmount(settlementVarPremiumAmount);
        return this;
    }

    public ContractDaoBuilder withSettlementFixPremiumAmount(BigDecimal settlementFixPremiumAmount) {
        contractDao.setSettlementFixPremiumAmount(settlementFixPremiumAmount);
        return this;
    }

    public ContractDaoBuilder withInsuranceCompanyProductId(String insuranceCompanyProductId) {
        contractDao.setInsuranceCompanyProductId(insuranceCompanyProductId);
        return this;
    }

    public ContractDaoBuilder withAutomaticDebitIndicatorType(String automaticDebitIndicatorType) {
        contractDao.setAutomaticDebitIndicatorType(automaticDebitIndicatorType);
        return this;
    }

    public ContractDaoBuilder withBiometryTransactionId(String biometryTransactionId) {
        contractDao.setBiometryTransactionId(biometryTransactionId);
        return this;
    }

    public ContractDaoBuilder withTelemarketingTransactionId(String telemarketingTransactionId) {
        contractDao.setTelemarketingTransactionId(telemarketingTransactionId);
        return this;
    }

    public ContractDaoBuilder withOriginalPaymentSubchannelId(String originalPaymentSubchannelId) {
        contractDao.setOriginalPaymentSubchannelId(originalPaymentSubchannelId);
        return this;
    }

    public InsuranceContractDAO build() {
        return contractDao;
    }

}
