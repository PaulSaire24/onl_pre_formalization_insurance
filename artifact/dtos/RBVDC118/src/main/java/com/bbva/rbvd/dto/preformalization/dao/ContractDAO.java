package com.bbva.rbvd.dto.preformalization.dao;

import java.math.BigDecimal;

public class ContractDAO {

    private String entityId;
    private String branchId;
    private String intAccountId;
    private String firstVerfnDigitId;
    private String secondVerfnDigitId;
    private BigDecimal insuranceProductId;
    private String policyQuotaInternalId;
    private String insuranceModalityType;
    private String policyId;
    private String insuranceManagerId;
    private String insurancePromoterId;
    private String contractManagerBranchId;
    private String contractInceptionDate;
    private String insuranceContractStartDate;
    private String insuranceContractEndDate;
    private BigDecimal validityMonthsNumber;
    private String insurancePolicyEndDate;
    private String domicileContractId;
    private String cardIssuingMarkType;
    private BigDecimal issuedReceiptNumber;
    private BigDecimal premiumAmount;
    private BigDecimal settlePendingPremiumAmount;
    private String lastInstallmentDate;
    private String installmentPeriodFinalDate;
    private BigDecimal insuredAmount;
    private String beneficiaryType;
    private BigDecimal renewalNumber;
    private String policyPymtPendDueDebtType;
    private String ctrctDisputeStatusType;
    private String periodNextPaymentDate;
    private String endorsementPolicyIndType;
    private String insrncCoContractStatusType;
    private String contPreviousSituationType;
    private String insurPendingDebtIndType;
    private BigDecimal totalDebtAmount;
    private BigDecimal prevPendBillRcptsNumber;
    private String insuranceCompanyProductId;
    private String automaticDebitIndicatorType;
    private String biometryTransactionId;
    private String telemarketingTransactionId;
    private String personalDocType;
    private String participantPersonalId;
    private String couponCode;
    private String originalPaymentSubchannelId;
    private String endLinkageDate;
    private BigDecimal insuranceCompanyId;
    private String customerId;
    private BigDecimal paymentFrequencyId;
    private String currencyId;
    private String contractStatusId;
    private String creationUserId;
    private String userAuditId;
    private BigDecimal settlementVarPremiumAmount;
    private BigDecimal settlementFixPremiumAmount;


    public ContractDAO() {
        this.cardIssuingMarkType = "N";
        this.beneficiaryType = "08";
        this.renewalNumber = BigDecimal.valueOf(0L);
        this.policyPymtPendDueDebtType = "N";
        this.endorsementPolicyIndType = "N";
        this.settlementVarPremiumAmount = BigDecimal.valueOf(0L);
    }


    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getIntAccountId() {
        return intAccountId;
    }

    public void setIntAccountId(String intAccountId) {
        this.intAccountId = intAccountId;
    }

    public String getFirstVerfnDigitId() {
        return firstVerfnDigitId;
    }

    public void setFirstVerfnDigitId(String firstVerfnDigitId) {
        this.firstVerfnDigitId = firstVerfnDigitId;
    }

    public String getSecondVerfnDigitId() {
        return secondVerfnDigitId;
    }

    public void setSecondVerfnDigitId(String secondVerfnDigitId) {
        this.secondVerfnDigitId = secondVerfnDigitId;
    }

    public String getPolicyQuotaInternalId() {
        return policyQuotaInternalId;
    }

    public void setPolicyQuotaInternalId(String policyQuotaInternalId) {
        this.policyQuotaInternalId = policyQuotaInternalId;
    }

    public String getInsuranceModalityType() {
        return insuranceModalityType;
    }

    public void setInsuranceModalityType(String insuranceModalityType) {
        this.insuranceModalityType = insuranceModalityType;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public String getInsuranceManagerId() {
        return insuranceManagerId;
    }

    public void setInsuranceManagerId(String insuranceManagerId) {
        this.insuranceManagerId = insuranceManagerId;
    }

    public String getInsurancePromoterId() {
        return insurancePromoterId;
    }

    public void setInsurancePromoterId(String insurancePromoterId) {
        this.insurancePromoterId = insurancePromoterId;
    }

    public String getContractManagerBranchId() {
        return contractManagerBranchId;
    }

    public void setContractManagerBranchId(String contractManagerBranchId) {
        this.contractManagerBranchId = contractManagerBranchId;
    }

    public String getContractInceptionDate() {
        return contractInceptionDate;
    }

    public void setContractInceptionDate(String contractInceptionDate) {
        this.contractInceptionDate = contractInceptionDate;
    }

    public String getInsuranceContractStartDate() {
        return insuranceContractStartDate;
    }

    public void setInsuranceContractStartDate(String insuranceContractStartDate) {
        this.insuranceContractStartDate = insuranceContractStartDate;
    }

    public String getInsuranceContractEndDate() {
        return insuranceContractEndDate;
    }

    public void setInsuranceContractEndDate(String insuranceContractEndDate) {
        this.insuranceContractEndDate = insuranceContractEndDate;
    }

    public BigDecimal getValidityMonthsNumber() {
        return validityMonthsNumber;
    }

    public void setValidityMonthsNumber(BigDecimal validityMonthsNumber) {
        this.validityMonthsNumber = validityMonthsNumber;
    }

    public String getInsurancePolicyEndDate() {
        return insurancePolicyEndDate;
    }

    public void setInsurancePolicyEndDate(String insurancePolicyEndDate) {
        this.insurancePolicyEndDate = insurancePolicyEndDate;
    }

    public String getDomicileContractId() {
        return domicileContractId;
    }

    public void setDomicileContractId(String domicileContractId) {
        this.domicileContractId = domicileContractId;
    }

    public String getCardIssuingMarkType() {
        return cardIssuingMarkType;
    }

    public void setCardIssuingMarkType(String cardIssuingMarkType) {
        this.cardIssuingMarkType = cardIssuingMarkType;
    }

    public BigDecimal getIssuedReceiptNumber() {
        return issuedReceiptNumber;
    }

    public void setIssuedReceiptNumber(BigDecimal issuedReceiptNumber) {
        this.issuedReceiptNumber = issuedReceiptNumber;
    }

    public BigDecimal getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(BigDecimal premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public BigDecimal getSettlePendingPremiumAmount() {
        return settlePendingPremiumAmount;
    }

    public void setSettlePendingPremiumAmount(BigDecimal settlePendingPremiumAmount) {
        this.settlePendingPremiumAmount = settlePendingPremiumAmount;
    }

    public String getLastInstallmentDate() {
        return lastInstallmentDate;
    }

    public void setLastInstallmentDate(String lastInstallmentDate) {
        this.lastInstallmentDate = lastInstallmentDate;
    }

    public String getInstallmentPeriodFinalDate() {
        return installmentPeriodFinalDate;
    }

    public void setInstallmentPeriodFinalDate(String installmentPeriodFinalDate) {
        this.installmentPeriodFinalDate = installmentPeriodFinalDate;
    }

    public BigDecimal getInsuredAmount() {
        return insuredAmount;
    }

    public void setInsuredAmount(BigDecimal insuredAmount) {
        this.insuredAmount = insuredAmount;
    }

    public String getBeneficiaryType() {
        return beneficiaryType;
    }

    public void setBeneficiaryType(String beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }

    public BigDecimal getRenewalNumber() {
        return renewalNumber;
    }

    public void setRenewalNumber(BigDecimal renewalNumber) {
        this.renewalNumber = renewalNumber;
    }

    public String getPolicyPymtPendDueDebtType() {
        return policyPymtPendDueDebtType;
    }

    public void setPolicyPymtPendDueDebtType(String policyPymtPendDueDebtType) {
        this.policyPymtPendDueDebtType = policyPymtPendDueDebtType;
    }

    public String getCtrctDisputeStatusType() {
        return ctrctDisputeStatusType;
    }

    public void setCtrctDisputeStatusType(String ctrctDisputeStatusType) {
        this.ctrctDisputeStatusType = ctrctDisputeStatusType;
    }

    public String getPeriodNextPaymentDate() {
        return periodNextPaymentDate;
    }

    public void setPeriodNextPaymentDate(String periodNextPaymentDate) {
        this.periodNextPaymentDate = periodNextPaymentDate;
    }

    public String getEndorsementPolicyIndType() {
        return endorsementPolicyIndType;
    }

    public void setEndorsementPolicyIndType(String endorsementPolicyIndType) {
        this.endorsementPolicyIndType = endorsementPolicyIndType;
    }

    public String getInsrncCoContractStatusType() {
        return insrncCoContractStatusType;
    }

    public void setInsrncCoContractStatusType(String insrncCoContractStatusType) {
        this.insrncCoContractStatusType = insrncCoContractStatusType;
    }

    public String getContPreviousSituationType() {
        return contPreviousSituationType;
    }

    public void setContPreviousSituationType(String contPreviousSituationType) {
        this.contPreviousSituationType = contPreviousSituationType;
    }

    public String getInsurPendingDebtIndType() {
        return insurPendingDebtIndType;
    }

    public void setInsurPendingDebtIndType(String insurPendingDebtIndType) {
        this.insurPendingDebtIndType = insurPendingDebtIndType;
    }

    public BigDecimal getTotalDebtAmount() {
        return totalDebtAmount;
    }

    public void setTotalDebtAmount(BigDecimal totalDebtAmount) {
        this.totalDebtAmount = totalDebtAmount;
    }

    public BigDecimal getPrevPendBillRcptsNumber() {
        return prevPendBillRcptsNumber;
    }

    public void setPrevPendBillRcptsNumber(BigDecimal prevPendBillRcptsNumber) {
        this.prevPendBillRcptsNumber = prevPendBillRcptsNumber;
    }

    public String getInsuranceCompanyProductId() {
        return insuranceCompanyProductId;
    }

    public void setInsuranceCompanyProductId(String insuranceCompanyProductId) {
        this.insuranceCompanyProductId = insuranceCompanyProductId;
    }

    public String getAutomaticDebitIndicatorType() {
        return automaticDebitIndicatorType;
    }

    public void setAutomaticDebitIndicatorType(String automaticDebitIndicatorType) {
        this.automaticDebitIndicatorType = automaticDebitIndicatorType;
    }

    public String getBiometryTransactionId() {
        return biometryTransactionId;
    }

    public void setBiometryTransactionId(String biometryTransactionId) {
        this.biometryTransactionId = biometryTransactionId;
    }

    public String getTelemarketingTransactionId() {
        return telemarketingTransactionId;
    }

    public void setTelemarketingTransactionId(String telemarketingTransactionId) {
        this.telemarketingTransactionId = telemarketingTransactionId;
    }

    public String getPersonalDocType() {
        return personalDocType;
    }

    public void setPersonalDocType(String personalDocType) {
        this.personalDocType = personalDocType;
    }

    public String getParticipantPersonalId() {
        return participantPersonalId;
    }

    public void setParticipantPersonalId(String participantPersonalId) {
        this.participantPersonalId = participantPersonalId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getOriginalPaymentSubchannelId() {
        return originalPaymentSubchannelId;
    }

    public void setOriginalPaymentSubchannelId(String originalPaymentSubchannelId) {
        this.originalPaymentSubchannelId = originalPaymentSubchannelId;
    }

    public String getEndLinkageDate() {
        return endLinkageDate;
    }

    public void setEndLinkageDate(String endLinkageDate) {
        this.endLinkageDate = endLinkageDate;
    }

    public BigDecimal getInsuranceProductId() {
        return insuranceProductId;
    }

    public void setInsuranceProductId(BigDecimal insuranceProductId) {
        this.insuranceProductId = insuranceProductId;
    }

    public BigDecimal getInsuranceCompanyId() {
        return insuranceCompanyId;
    }

    public void setInsuranceCompanyId(BigDecimal insuranceCompanyId) {
        this.insuranceCompanyId = insuranceCompanyId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getPaymentFrequencyId() {
        return paymentFrequencyId;
    }

    public void setPaymentFrequencyId(BigDecimal paymentFrequencyId) {
        this.paymentFrequencyId = paymentFrequencyId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getContractStatusId() {
        return contractStatusId;
    }

    public void setContractStatusId(String contractStatusId) {
        this.contractStatusId = contractStatusId;
    }

    public String getCreationUserId() {
        return creationUserId;
    }

    public void setCreationUserId(String creationUserId) {
        this.creationUserId = creationUserId;
    }

    public String getUserAuditId() {
        return userAuditId;
    }

    public void setUserAuditId(String userAuditId) {
        this.userAuditId = userAuditId;
    }

    public BigDecimal getSettlementFixPremiumAmount() {
        return settlementFixPremiumAmount;
    }

    public void setSettlementFixPremiumAmount(BigDecimal settlementFixPremiumAmount) {
        this.settlementFixPremiumAmount = settlementFixPremiumAmount;
    }

    public BigDecimal getSettlementVarPremiumAmount() {
        return settlementVarPremiumAmount;
    }

    public void setSettlementVarPremiumAmount(BigDecimal settlementVarPremiumAmount) {
        this.settlementVarPremiumAmount = settlementVarPremiumAmount;
    }

    @Override
    public String toString() {
        return "ContractDAO{" +
                "entityId='" + entityId + '\'' +
                ", branchId='" + branchId + '\'' +
                ", intAccountId='" + intAccountId + '\'' +
                ", firstVerfnDigitId='" + firstVerfnDigitId + '\'' +
                ", secondVerfnDigitId='" + secondVerfnDigitId + '\'' +
                ", insuranceProductId=" + insuranceProductId +
                ", policyQuotaInternalId='" + policyQuotaInternalId + '\'' +
                ", insuranceModalityType='" + insuranceModalityType + '\'' +
                ", policyId='" + policyId + '\'' +
                ", insuranceManagerId='" + insuranceManagerId + '\'' +
                ", insurancePromoterId='" + insurancePromoterId + '\'' +
                ", contractManagerBranchId='" + contractManagerBranchId + '\'' +
                ", contractInceptionDate='" + contractInceptionDate + '\'' +
                ", insuranceContractStartDate='" + insuranceContractStartDate + '\'' +
                ", insuranceContractEndDate='" + insuranceContractEndDate + '\'' +
                ", validityMonthsNumber=" + validityMonthsNumber +
                ", insurancePolicyEndDate='" + insurancePolicyEndDate + '\'' +
                ", domicileContractId='" + domicileContractId + '\'' +
                ", cardIssuingMarkType='" + cardIssuingMarkType + '\'' +
                ", issuedReceiptNumber=" + issuedReceiptNumber +
                ", premiumAmount=" + premiumAmount +
                ", settlePendingPremiumAmount=" + settlePendingPremiumAmount +
                ", lastInstallmentDate='" + lastInstallmentDate + '\'' +
                ", installmentPeriodFinalDate='" + installmentPeriodFinalDate + '\'' +
                ", insuredAmount=" + insuredAmount +
                ", beneficiaryType='" + beneficiaryType + '\'' +
                ", renewalNumber=" + renewalNumber +
                ", policyPymtPendDueDebtType='" + policyPymtPendDueDebtType + '\'' +
                ", ctrctDisputeStatusType='" + ctrctDisputeStatusType + '\'' +
                ", periodNextPaymentDate='" + periodNextPaymentDate + '\'' +
                ", endorsementPolicyIndType='" + endorsementPolicyIndType + '\'' +
                ", insrncCoContractStatusType='" + insrncCoContractStatusType + '\'' +
                ", contPreviousSituationType='" + contPreviousSituationType + '\'' +
                ", insurPendingDebtIndType='" + insurPendingDebtIndType + '\'' +
                ", totalDebtAmount=" + totalDebtAmount +
                ", prevPendBillRcptsNumber=" + prevPendBillRcptsNumber +
                ", insuranceCompanyProductId='" + insuranceCompanyProductId + '\'' +
                ", automaticDebitIndicatorType='" + automaticDebitIndicatorType + '\'' +
                ", biometryTransactionId='" + biometryTransactionId + '\'' +
                ", telemarketingTransactionId='" + telemarketingTransactionId + '\'' +
                ", personalDocType='" + personalDocType + '\'' +
                ", participantPersonalId='" + participantPersonalId + '\'' +
                ", couponCode='" + couponCode + '\'' +
                ", originalPaymentSubchannelId='" + originalPaymentSubchannelId + '\'' +
                ", endLinkageDate='" + endLinkageDate + '\'' +
                ", insuranceCompanyId=" + insuranceCompanyId +
                ", customerId='" + customerId + '\'' +
                ", paymentFrequencyId=" + paymentFrequencyId +
                ", currencyId='" + currencyId + '\'' +
                ", contractStatusId='" + contractStatusId + '\'' +
                ", creationUserId='" + creationUserId + '\'' +
                ", userAuditId='" + userAuditId + '\'' +
                ", settlementVarPremiumAmount=" + settlementVarPremiumAmount +
                ", settlementFixPremiumAmount=" + settlementFixPremiumAmount +
                '}';
    }
}
