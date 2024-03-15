package com.bbva.rbvd;

import com.bbva.elara.transaction.AbstractTransaction;
import com.bbva.rbvd.dto.insrncsale.commons.BankDTO;
import com.bbva.rbvd.dto.insrncsale.commons.HolderDTO;
import com.bbva.rbvd.dto.insrncsale.commons.PolicyInspectionDTO;
import com.bbva.rbvd.dto.insrncsale.commons.ValidityPeriodDTO;
import com.bbva.rbvd.dto.insrncsale.policy.BusinessAgentDTO;
import com.bbva.rbvd.dto.insrncsale.policy.FirstInstallmentDTO;
import com.bbva.rbvd.dto.insrncsale.policy.InsuranceCompanyDTO;
import com.bbva.rbvd.dto.insrncsale.policy.InsuredAmountDTO;
import com.bbva.rbvd.dto.insrncsale.policy.ParticipantDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyInstallmentPlanDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyPaymentMethodDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PromoterDTO;
import com.bbva.rbvd.dto.insrncsale.policy.TotalAmountDTO;
import com.bbva.rbvd.dto.preformalization.*;

import java.util.List;

/**
 * In this class, the input and output data is defined automatically through the setters and getters.
 */
public abstract class AbstractRBVDT11801PETransaction extends AbstractTransaction {

    public AbstractRBVDT11801PETransaction() {
    }


    /**
     * Return value for input parameter alias
     */
    protected String getAlias() {
        return (String) this.getParameter("alias");
    }

    /**
     * Return value for input parameter quotationNumber
     */
    protected String getQuotationnumber() {
        return (String) this.getParameter("quotationNumber");
    }

    /**
     * Return value for input parameter product
     */
    protected Product getProduct() {
        return (Product) this.getParameter("product");
    }

    /**
     * Return value for input parameter holder
     */
    protected HolderDTO getHolder() {
        return (HolderDTO) this.getParameter("holder");
    }

    /**
     * Return value for input parameter paymentMethod
     */
    protected PolicyPaymentMethodDTO getPaymentmethod() {
        return (PolicyPaymentMethodDTO) this.getParameter("paymentMethod");
    }

    /**
     * Return value for input parameter validityPeriod
     */
    protected ValidityPeriod getValidityperiod() {
        return (ValidityPeriod) this.getParameter("validityPeriod");
    }

    /**
     * Return value for input parameter totalAmount
     */
    protected TotalAmountDTO getTotalAmount() {
        return (TotalAmountDTO) this.getParameter("totalAmount");
    }

    /**
     * Return value for input parameter insuredAmount
     */
    protected InsuredAmountDTO getInsuredAmount() {
        return (InsuredAmountDTO) this.getParameter("insuredAmount");
    }

    /**
     * Return value for input parameter isDataTreatment
     */
    protected Boolean getIsDataTreatment() {
        return (Boolean) this.getParameter("isDataTreatment");
    }

    /**
     * Return value for input parameter relatedContracts
     */
    protected List<RelatedContract> getRelatedContracts() {
        return (List<RelatedContract>) this.getParameter("relatedContracts");
    }

    /**
     * Return value for input parameter installmentPlan
     */
    protected PolicyInstallmentPlanDTO getInstallmentPlan() {
        return (PolicyInstallmentPlanDTO) this.getParameter("installmentPlan");
    }

    /**
     * Return value for input parameter hasAcceptedContract
     */
    protected Boolean getHasAcceptedContract() {
        return (Boolean) this.getParameter("hasAcceptedContract");
    }

    /**
     * Return value for input parameter inspection
     */
    protected com.bbva.rbvd.dto.insrncsale.commons.PolicyInspectionDTO getInspection() {
        return (com.bbva.rbvd.dto.insrncsale.commons.PolicyInspectionDTO) this.getParameter("inspection");
    }

    /**
     * Return value for input parameter firstInstallment
     */
    protected FirstInstallmentDTO getFirstInstallment() {
        return (FirstInstallmentDTO) this.getParameter("firstInstallment");
    }

    /**
     * Return value for input parameter participants
     */
    protected List<ParticipantDTO> getParticipants() {
        return (List<ParticipantDTO>) this.getParameter("participants");
    }

    /**
     * Return value for input parameter businessAgent
     */
    protected BusinessAgentDTO getBusinessAgent() {
        return (BusinessAgentDTO) this.getParameter("businessAgent");
    }

    /**
     * Return value for input parameter promoter
     */
    protected PromoterDTO getPromoter() {
        return (PromoterDTO) this.getParameter("promoter");
    }

    /**
     * Return value for input parameter insuranceCompany
     */
    protected InsuranceCompanyDTO getInsuranceCompany() {
        return (InsuranceCompanyDTO) this.getParameter("insuranceCompany");
    }

    /**
     * Return value for input parameter bank
     */
    protected com.bbva.rbvd.dto.insrncsale.commons.BankDTO getBank() {
        return (com.bbva.rbvd.dto.insrncsale.commons.BankDTO) this.getParameter("bank");
    }

    /**
     * Return value for input parameter couponCode
     */
    protected String getCouponCode() {
        return (String) this.getParameter("couponCode");
    }

    /**
     * Return value for input parameter identityVerificationCode
     */
    protected String getIdentityVerificationCode() {
        return (String) this.getParameter("identityVerificationCode");
    }

    public void setId(String id) {
        this.addParameter("id", id);
    }

    public void setQuotationNumber(String quotationNumber) {
        this.addParameter("quotationNumber", quotationNumber);
    }

    public void setPolicyNumber(String policyNumber) {
        this.addParameter("policyNumber", policyNumber);
    }

    public void setAlias(String alias) {
        this.addParameter("alias", alias);
    }

    public void setProductType(String productType) {
        this.addParameter("productType", productType);
    }

    public void setProduct(Product product) {
        this.addParameter("product", product);
    }

    public void setHasAcceptedContract(Boolean hasAcceptedContract) {
        this.addParameter("hasAcceptedContract", hasAcceptedContract);
    }

    public void setIsDataTreatment(Boolean isDataTreatment) {
        this.addParameter("isDataTreatment", isDataTreatment);
    }

    public void setPaymentMethod(PolicyPaymentMethodDTO paymentMethod) {
        this.addParameter("paymentMethod", paymentMethod);
    }

    public void setParticipants(List<ParticipantDTO> participants) {
        this.addParameter("participants", participants);
    }

    public void setFirstInstallment(FirstInstallmentDTO firstInstallment) {
        this.addParameter("firstInstallment", firstInstallment);
    }

    public void setPromoter(PromoterDTO promoter) {
        this.addParameter("promoter", promoter);
    }

    public void setInspection(PolicyInspectionDTO inspection) {
        this.addParameter("inspection", inspection);
    }

    public void setRelatedContracts(List<RelatedContract> relatedContracts) {
        this.addParameter("relatedContracts", relatedContracts);
    }

    public void setTotalAmount(TotalAmountDTO totalAmount) {
        this.addParameter("totalAmount", totalAmount);
    }

    public void setTotalAmountWithoutTax(TotalAmountDTO totalAmountWithoutTax) {
        this.addParameter("totalAmountWithoutTax", totalAmountWithoutTax);
    }

    public void setInsuredAmount(InsuredAmountDTO insuredAmount) {
        this.addParameter("insuredAmount", insuredAmount);
    }

    public void setInstallmentPlan(PolicyInstallmentPlanDTO installmentPlan) {
        this.addParameter("installmentPlan", installmentPlan);
    }

    public void setOperationDate(String operationDate) {
        this.addParameter("operationDate", operationDate);
    }

    public void setStatus(InsuranceStatus status) {
        this.addParameter("status", status);
    }

    public void setInsuranceCompany(InsuranceCompanyDTO insuranceCompany) {
        this.addParameter("insuranceCompany", insuranceCompany);
    }

    public void setPaymentConfiguration(PaymentConfiguration paymentConfiguration) {
        this.addParameter("paymentConfiguration", paymentConfiguration);
    }

    public void setHolder(HolderDTO holder) {
        this.addParameter("holder", holder);
    }

    public void setCancelationDate(String cancelationDate) {
        this.addParameter("cancelationDate", cancelationDate);
    }

    public void setValidityPeriod(ValidityPeriod validityPeriod) {
        this.addParameter("validityPeriod", validityPeriod);
    }

    public void setCurrentInstallment(Installment currentInstallment) {
        this.addParameter("currentInstallment", currentInstallment);
    }

    public void setPremiumDebt(TotalAmountDTO premiumDebt) {
        this.addParameter("premiumDebt", premiumDebt);
    }

    public void setRenewalPolicy(RenewalPolicy renewalPolicy) {
        this.addParameter("renewalPolicy", renewalPolicy);
    }

    public void setCertificateNumber(String certificateNumber) {
        this.addParameter("certificateNumber", certificateNumber);
    }

    public void setSubscriptionType(String subscriptionType) {
        this.addParameter("subscriptionType", subscriptionType);
    }

    public void setBusinessAgent(BusinessAgentDTO businessAgent) {
        this.addParameter("businessAgent", businessAgent);
    }

    public void setBank(BankDTO bank) {
        this.addParameter("bank", bank);
    }

    public void setLastInstallment(Installment lastInstallment) {
        this.addParameter("lastInstallment", lastInstallment);
    }

    public void setExternalDocumentationSendDate(String externalDocumentationSendDate) {
        this.addParameter("externalDocumentationSendDate", externalDocumentationSendDate);
    }

    public void setNonRenewalPolicy(RenewalPolicy nonRenewalPolicy) {
        this.addParameter("nonRenewalPolicy", nonRenewalPolicy);
    }

    public void setCouponCode(String couponCode) {
        this.addParameter("couponCode", couponCode);
    }
}
