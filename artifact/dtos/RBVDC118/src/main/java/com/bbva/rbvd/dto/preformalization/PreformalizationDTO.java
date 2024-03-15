package com.bbva.rbvd.dto.preformalization;

import com.bbva.rbvd.dto.insrncsale.commons.CommonFieldsDTO;
import com.bbva.rbvd.dto.insrncsale.policy.*;

import java.util.List;

public class PreformalizationDTO extends CommonFieldsDTO {
    private Product product;
    private TotalAmountDTO totalAmount;
    private TotalAmountDTO totalAmountWithoutTax;
    private InsuredAmountDTO insuredAmount;
    private InsuranceCompanyDTO insuranceCompany;
    private boolean isDataTreatment;
    private List<RelatedContract> relatedContracts;
    private PolicyInstallmentPlanDTO installmentPlan;
    private boolean hasAcceptedContract;
    private FirstInstallmentDTO firstInstallment;
    private List<ParticipantDTO> participants;
    private BusinessAgentDTO businessAgent;
    private PromoterDTO promoter;
    private String policyNumber;
    private String quotationNumber;
    private String alias;
    private String productType;
    private InsuranceStatus insuranceStatus;
    private PaymentConfiguration paymentConfiguration;
    private String cancelationDate;
    private ValidityPeriod insuranceValidityPeriod;
    private Installment currentInstallment;
    private TotalAmountDTO premiumDebt;
    private transient RenewalPolicy renewalPolicy;
    private String certificateNumber;
    private String subscriptionType;
    private Installment lastInstallment;
    private transient RenewalPolicy nonRenewalPolicy;
    private String identityVerificationCode;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public TotalAmountDTO getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(TotalAmountDTO totalAmount) {
        this.totalAmount = totalAmount;
    }

    public TotalAmountDTO getTotalAmountWithoutTax() {
        return totalAmountWithoutTax;
    }

    public void setTotalAmountWithoutTax(TotalAmountDTO totalAmountWithoutTax) {
        this.totalAmountWithoutTax = totalAmountWithoutTax;
    }

    public InsuredAmountDTO getInsuredAmount() {
        return insuredAmount;
    }

    public void setInsuredAmount(InsuredAmountDTO insuredAmount) {
        this.insuredAmount = insuredAmount;
    }

    public InsuranceCompanyDTO getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(InsuranceCompanyDTO insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public boolean isDataTreatment() {
        return isDataTreatment;
    }

    public void setDataTreatment(boolean dataTreatment) {
        isDataTreatment = dataTreatment;
    }

    public List<RelatedContract> getRelatedContracts() {
        return relatedContracts;
    }

    public void setRelatedContracts(List<RelatedContract> relatedContracts) {
        this.relatedContracts = relatedContracts;
    }

    public PolicyInstallmentPlanDTO getInstallmentPlan() {
        return installmentPlan;
    }

    public void setInstallmentPlan(PolicyInstallmentPlanDTO installmentPlan) {
        this.installmentPlan = installmentPlan;
    }

    public boolean isHasAcceptedContract() {
        return hasAcceptedContract;
    }

    public void setHasAcceptedContract(boolean hasAcceptedContract) {
        this.hasAcceptedContract = hasAcceptedContract;
    }

    public FirstInstallmentDTO getFirstInstallment() {
        return firstInstallment;
    }

    public void setFirstInstallment(FirstInstallmentDTO firstInstallment) {
        this.firstInstallment = firstInstallment;
    }

    public List<ParticipantDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantDTO> participants) {
        this.participants = participants;
    }

    public BusinessAgentDTO getBusinessAgent() {
        return businessAgent;
    }

    public void setBusinessAgent(BusinessAgentDTO businessAgent) {
        this.businessAgent = businessAgent;
    }

    public PromoterDTO getPromoter() {
        return promoter;
    }

    public void setPromoter(PromoterDTO promoter) {
        this.promoter = promoter;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getQuotationNumber() {
        return quotationNumber;
    }

    public void setQuotationNumber(String quotationNumber) {
        this.quotationNumber = quotationNumber;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public InsuranceStatus getInsuranceStatus() {
        return insuranceStatus;
    }

    public void setInsuranceStatus(InsuranceStatus insuranceStatus) {
        this.insuranceStatus = insuranceStatus;
    }

    public PaymentConfiguration getPaymentConfiguration() {
        return paymentConfiguration;
    }

    public void setPaymentConfiguration(PaymentConfiguration paymentConfiguration) {
        this.paymentConfiguration = paymentConfiguration;
    }

    public String getCancelationDate() {
        return cancelationDate;
    }

    public void setCancelationDate(String cancelationDate) {
        this.cancelationDate = cancelationDate;
    }

    public ValidityPeriod getInsuranceValidityPeriod() {
        return insuranceValidityPeriod;
    }

    public void setInsuranceValidityPeriod(ValidityPeriod insuranceValidityPeriod) {
        this.insuranceValidityPeriod = insuranceValidityPeriod;
    }

    public Installment getCurrentInstallment() {
        return currentInstallment;
    }

    public void setCurrentInstallment(Installment currentInstallment) {
        this.currentInstallment = currentInstallment;
    }

    public TotalAmountDTO getPremiumDebt() {
        return premiumDebt;
    }

    public void setPremiumDebt(TotalAmountDTO premiumDebt) {
        this.premiumDebt = premiumDebt;
    }

    public RenewalPolicy getRenewalPolicy() {
        return renewalPolicy;
    }

    public void setRenewalPolicy(RenewalPolicy renewalPolicy) {
        this.renewalPolicy = renewalPolicy;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Installment getLastInstallment() {
        return lastInstallment;
    }

    public void setLastInstallment(Installment lastInstallment) {
        this.lastInstallment = lastInstallment;
    }

    public RenewalPolicy getNonRenewalPolicy() {
        return nonRenewalPolicy;
    }

    public void setNonRenewalPolicy(RenewalPolicy nonRenewalPolicy) {
        this.nonRenewalPolicy = nonRenewalPolicy;
    }

    public String getIdentityVerificationCode() {
        return identityVerificationCode;
    }

    public void setIdentityVerificationCode(String identityVerificationCode) {
        this.identityVerificationCode = identityVerificationCode;
    }

    @Override
    public String toString() {
        return "PreformalizationDTO{" +
                "product=" + product +
                ", totalAmount=" + totalAmount +
                ", totalAmountWithoutTax=" + totalAmountWithoutTax +
                ", insuredAmount=" + insuredAmount +
                ", insuranceCompany=" + insuranceCompany +
                ", isDataTreatment=" + isDataTreatment +
                ", relatedContracts=" + relatedContracts +
                ", installmentPlan=" + installmentPlan +
                ", hasAcceptedContract=" + hasAcceptedContract +
                ", firstInstallment=" + firstInstallment +
                ", participants=" + participants +
                ", businessAgent=" + businessAgent +
                ", promoter=" + promoter +
                ", policyNumber='" + policyNumber + '\'' +
                ", quotationNumber='" + quotationNumber + '\'' +
                ", alias='" + alias + '\'' +
                ", productType='" + productType + '\'' +
                ", insuranceStatus=" + insuranceStatus +
                ", paymentConfiguration=" + paymentConfiguration +
                ", cancelationDate='" + cancelationDate + '\'' +
                ", insuranceValidityPeriod=" + insuranceValidityPeriod +
                ", currentInstallment=" + currentInstallment +
                ", premiumDebt=" + premiumDebt +
                ", renewalPolicy=" + renewalPolicy +
                ", certificateNumber='" + certificateNumber + '\'' +
                ", subscriptionType='" + subscriptionType + '\'' +
                ", lastInstallment=" + lastInstallment +
                ", nonRenewalPolicy=" + nonRenewalPolicy +
                ", identityVerificationCode='" + identityVerificationCode + '\'' +
                '}';
    }
}
