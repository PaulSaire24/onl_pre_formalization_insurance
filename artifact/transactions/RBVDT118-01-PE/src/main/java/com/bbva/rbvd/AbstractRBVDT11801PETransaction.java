package com.bbva.rbvd;

import com.bbva.elara.transaction.AbstractTransaction;
import com.bbva.rbvd.dto.insrncsale.commons.*;
import com.bbva.rbvd.dto.insrncsale.offer.PolicyDurationDTO;
import com.bbva.rbvd.dto.insrncsale.policy.*;

import java.util.Calendar;
import java.util.List;

/**
 * In this class, the input and output data is defined automatically through the setters and getters.
 */
public abstract class AbstractRBVDT11801PETransaction extends AbstractTransaction {

    public AbstractRBVDT11801PETransaction() {
    }

    /**
     * Return value for input parameter quotationId
     */
    protected String getQuotationid() {
        return (String) this.getParameter("quotationId");
    }

    /**
     * Set value for String output parameter quotationId
     */
    protected void setQuotationid(final String field) {
        this.addParameter("quotationId", field);
    }

    /**
     * Return value for input parameter productId
     */
    protected String getProductid() {
        return (String) this.getParameter("productId");
    }

    /**
     * Set value for String output parameter productId
     */
    protected void setProductid(final String field) {
        this.addParameter("productId", field);
    }

    /**
     * Return value for input parameter productPlan
     */
    protected PolicyProductPlan getProductplan() {
        return (PolicyProductPlan) this.getParameter("productPlan");
    }

    /**
     * Set value for PolicyProductPlan output parameter productPlan
     */
    protected void setProductplan(final PolicyProductPlan field) {
        this.addParameter("productPlan", field);
    }

    /**
     * Return value for input parameter paymentMethod
     */
    protected PolicyPaymentMethodDTO getPaymentmethod() {
        return (PolicyPaymentMethodDTO) this.getParameter("paymentMethod");
    }

    /**
     * Set value for PolicyPaymentMethodDTO output parameter paymentMethod
     */
    protected void setPaymentmethod(final PolicyPaymentMethodDTO field) {
        this.addParameter("paymentMethod", field);
    }

    /**
     * Return value for input parameter validityPeriod
     */
    protected ValidityPeriodDTO getValidityperiod() {
        return (ValidityPeriodDTO) this.getParameter("validityPeriod");
    }

    /**
     * Set value for ValidityPeriodDTO output parameter validityPeriod
     */
    protected void setValidityperiod(final ValidityPeriodDTO field) {
        this.addParameter("validityPeriod", field);
    }

    /**
     * Return value for input parameter totalAmount
     */
    protected TotalAmountDTO getTotalamount() {
        return (TotalAmountDTO) this.getParameter("totalAmount");
    }

    /**
     * Set value for TotalAmountDTO output parameter totalAmount
     */
    protected void setTotalamount(final TotalAmountDTO field) {
        this.addParameter("totalAmount", field);
    }

    /**
     * Return value for input parameter insuredAmount
     */
    protected InsuredAmountDTO getInsuredamount() {
        return (InsuredAmountDTO) this.getParameter("insuredAmount");
    }

    /**
     * Set value for InsuredAmountDTO output parameter insuredAmount
     */
    protected void setInsuredamount(final InsuredAmountDTO field) {
        this.addParameter("insuredAmount", field);
    }

    /**
     * Return value for input parameter isDataTreatment
     */
    protected Boolean getIsdatatreatment() {
        return (Boolean) this.getParameter("isDataTreatment");
    }

    /**
     * Set value for Boolean output parameter isDataTreatment
     */
    protected void setIsdatatreatment(final Boolean field) {
        this.addParameter("isDataTreatment", field);
    }

    /**
     * Return value for input parameter holder
     */
    protected HolderDTO getHolder() {
        return (HolderDTO) this.getParameter("holder");
    }

    /**
     * Set value for HolderDTO output parameter holder
     */
    protected void setHolder(final HolderDTO field) {
        this.addParameter("holder", field);
    }

    /**
     * Return value for input parameter relatedContracts
     */
    protected List<RelatedContractDTO> getRelatedcontracts() {
        return (List<RelatedContractDTO>) this.getParameter("relatedContracts");
    }

    /**
     * Set value for List<RelatedContractDTO> output parameter relatedContracts
     */
    protected void setRelatedcontracts(final List<RelatedContractDTO> field) {
        this.addParameter("relatedContracts", field);
    }

    /**
     * Return value for input parameter installmentPlan
     */
    protected PolicyInstallmentPlanDTO getInstallmentplan() {
        return (PolicyInstallmentPlanDTO) this.getParameter("installmentPlan");
    }

    /**
     * Set value for PolicyInstallmentPlanDTO output parameter installmentPlan
     */
    protected void setInstallmentplan(final PolicyInstallmentPlanDTO field) {
        this.addParameter("installmentPlan", field);
    }

    /**
     * Return value for input parameter hasAcceptedContract
     */
    protected Boolean getHasacceptedcontract() {
        return (Boolean) this.getParameter("hasAcceptedContract");
    }

    /**
     * Set value for Boolean output parameter hasAcceptedContract
     */
    protected void setHasacceptedcontract(final Boolean field) {
        this.addParameter("hasAcceptedContract", field);
    }

    /**
     * Return value for input parameter inspection
     */
    protected PolicyInspectionDTO getInspection() {
        return (PolicyInspectionDTO) this.getParameter("inspection");
    }

    /**
     * Set value for PolicyInspectionDTO output parameter inspection
     */
    protected void setInspection(final PolicyInspectionDTO field) {
        this.addParameter("inspection", field);
    }

    /**
     * Return value for input parameter firstInstallment
     */
    protected FirstInstallmentDTO getFirstinstallment() {
        return (FirstInstallmentDTO) this.getParameter("firstInstallment");
    }

    /**
     * Set value for FirstInstallmentDTO output parameter firstInstallment
     */
    protected void setFirstinstallment(final FirstInstallmentDTO field) {
        this.addParameter("firstInstallment", field);
    }

    /**
     * Return value for input parameter participants
     */
    protected List<ParticipantDTO> getParticipants() {
        return (List<ParticipantDTO>) this.getParameter("participants");
    }

    /**
     * Set value for List<ParticipantDTO> output parameter participants
     */
    protected void setParticipants(final List<ParticipantDTO> field) {
        this.addParameter("participants", field);
    }

    /**
     * Return value for input parameter businessAgent
     */
    protected BusinessAgentDTO getBusinessagent() {
        return (BusinessAgentDTO) this.getParameter("businessAgent");
    }

    /**
     * Set value for BusinessAgentDTO output parameter businessAgent
     */
    protected void setBusinessagent(final BusinessAgentDTO field) {
        this.addParameter("businessAgent", field);
    }

    /**
     * Return value for input parameter promoter
     */
    protected PromoterDTO getPromoter() {
        return (PromoterDTO) this.getParameter("promoter");
    }

    /**
     * Set value for PromoterDTO output parameter promoter
     */
    protected void setPromoter(final PromoterDTO field) {
        this.addParameter("promoter", field);
    }

    /**
     * Return value for input parameter bank
     */
    protected BankDTO getBank() {
        return (BankDTO) this.getParameter("bank");
    }

    /**
     * Set value for BankDTO output parameter bank
     */
    protected void setBank(final BankDTO field) {
        this.addParameter("bank", field);
    }

    /**
     * Return value for input parameter identityVerificationCode
     */
    protected String getIdentityverificationcode() {
        return (String) this.getParameter("identityVerificationCode");
    }

    /**
     * Set value for String output parameter identityVerificationCode
     */
    protected void setIdentityverificationcode(final String field) {
        this.addParameter("identityVerificationCode", field);
    }

    /**
     * Return value for input parameter insuranceCompany
     */
    protected InsuranceCompanyDTO getInsurancecompany() {
        return (InsuranceCompanyDTO) this.getParameter("insuranceCompany");
    }

    /**
     * Set value for InsuranceCompanyDTO output parameter insuranceCompany
     */
    protected void setInsurancecompany(final InsuranceCompanyDTO field) {
        this.addParameter("insuranceCompany", field);
    }

    /**
     * Return value for input parameter couponCode
     */
    protected String getCouponcode() {
        return (String) this.getParameter("couponCode");
    }

    /**
     * Set value for String output parameter couponCode
     */
    protected void setCouponcode(final String field) {
        this.addParameter("couponCode", field);
    }

    /**
     * Return value for input parameter deliveries
     */
    protected List<DeliveryDTO> getDeliveries() {
        return (List<DeliveryDTO>) this.getParameter("deliveries");
    }

    /**
     * Set value for List<DeliveryDTO> output parameter deliveries
     */
    protected void setDeliveries(final List<DeliveryDTO> field) {
        this.addParameter("deliveries", field);
    }

    /**
     * Return value for input parameter saleSupplier
     */
    protected SaleSupplierDTO getSalesupplier() {
        return (SaleSupplierDTO) this.getParameter("saleSupplier");
    }

    /**
     * Set value for SaleSupplierDTO output parameter saleSupplier
     */
    protected void setSalesupplier(final SaleSupplierDTO field) {
        this.addParameter("saleSupplier", field);
    }

    /**
     * Set value for String output parameter id
     */
    protected void setId(final String field) {
        this.addParameter("id", field);
    }

    /**
     * Set value for String output parameter policyNumber
     */
    protected void setPolicynumber(final String field) {
        this.addParameter("policyNumber", field);
    }

    /**
     * Set value for String output parameter productDescription
     */
    protected void setProductdescription(final String field) {
        this.addParameter("productDescription", field);
    }

    /**
     * Set value for Calendar output parameter operationDate
     */
    protected void setOperationdate(final Calendar field) {
        this.addParameter("operationDate", field);
    }

    /**
     * Set value for List<LinkDTO> output parameter links
     */
    protected void setLinks(final List<LinkDTO> field) {
        this.addParameter("links", field);
    }

    /**
     * Set value for String output parameter externalQuotationId
     */
    protected void setExternalquotationid(final String field) {
        this.addParameter("externalQuotationId", field);
    }

    /**
     * Set value for String output parameter externalPolicyNumber
     */
    protected void setExternalpolicynumber(final String field) {
        this.addParameter("externalPolicyNumber", field);
    }

    /**
     * Set value for QuotationStatusDTO output parameter status
     */
    protected void setStatus(final QuotationStatusDTO field) {
        this.addParameter("status", field);
    }

    /**
     * Set value for PolicyDurationDTO output parameter policyDuration
     */
    protected void setPolicyduration(final PolicyDurationDTO field) {
        this.addParameter("policyDuration", field);
    }

    /**
     * Set value for PaymentAmountDTO output parameter totalAmountWithoutTax
     */
    protected void setTotalamountwithouttax(final TotalAmountDTO field) {
        this.addParameter("totalAmountWithoutTax", field);
    }
}
