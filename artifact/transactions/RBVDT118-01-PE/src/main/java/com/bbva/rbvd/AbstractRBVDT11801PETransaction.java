package com.bbva.rbvd;

import com.bbva.elara.transaction.AbstractTransaction;
import com.bbva.rbvd.dto.insrncsale.commons.BankDTO;
import com.bbva.rbvd.dto.insrncsale.commons.HolderDTO;
import com.bbva.rbvd.dto.insrncsale.commons.PolicyInspectionDTO;
import com.bbva.rbvd.dto.insrncsale.commons.QuotationStatusDTO;
import com.bbva.rbvd.dto.insrncsale.commons.ValidityPeriodDTO;
import com.bbva.rbvd.dto.insrncsale.policy.BusinessAgentDTO;
import com.bbva.rbvd.dto.insrncsale.policy.CurrentInstallmentDTO;
import com.bbva.rbvd.dto.insrncsale.policy.FirstInstallmentDTO;
import com.bbva.rbvd.dto.insrncsale.policy.InstallmentPlansDTO;
import com.bbva.rbvd.dto.insrncsale.policy.InsuranceCompanyDTO;
import com.bbva.rbvd.dto.insrncsale.policy.InsuredAmountDTO;
import com.bbva.rbvd.dto.insrncsale.policy.NonRenewalPolicyDTO;
import com.bbva.rbvd.dto.insrncsale.policy.ParticipantDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PaymentConfigurationDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyInstallmentPlanDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyPaymentMethodDTO;
import com.bbva.rbvd.dto.insrncsale.policy.ProductDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PromoterDTO;
import com.bbva.rbvd.dto.insrncsale.policy.RelatedContractDTO;
import com.bbva.rbvd.dto.insrncsale.policy.RenewalPolicyDTO;
import com.bbva.rbvd.dto.insrncsale.policy.TotalAmountDTO;
import java.util.Date;
import java.util.List;

/**
 * In this class, the input and output data is defined automatically through the setters and getters.
 */
public abstract class AbstractRBVDT11801PETransaction extends AbstractTransaction {

	public AbstractRBVDT11801PETransaction(){
	}


	/**
	 * Return value for input parameter quotationNumber
	 */
	protected String getQuotationnumber(){
		return (String)this.getParameter("quotationNumber");
	}

	/**
	 * Return value for input parameter product
	 */
	protected ProductDTO getProduct(){
		return (ProductDTO)this.getParameter("product");
	}

	/**
	 * Return value for input parameter holder
	 */
	protected HolderDTO getHolder(){
		return (HolderDTO)this.getParameter("holder");
	}

	/**
	 * Return value for input parameter paymentMethod
	 */
	protected PolicyPaymentMethodDTO getPaymentmethod(){
		return (PolicyPaymentMethodDTO)this.getParameter("paymentMethod");
	}

	/**
	 * Return value for input parameter validityPeriod
	 */
	protected ValidityPeriodDTO getValidityperiod(){
		return (ValidityPeriodDTO)this.getParameter("validityPeriod");
	}

	/**
	 * Return value for input parameter totalAmount
	 */
	protected TotalAmountDTO getTotalamount(){
		return (TotalAmountDTO)this.getParameter("totalAmount");
	}

	/**
	 * Return value for input parameter insuredAmount
	 */
	protected InsuredAmountDTO getInsuredamount(){
		return (InsuredAmountDTO)this.getParameter("insuredAmount");
	}

	/**
	 * Return value for input parameter isDataTreatment
	 */
	protected Boolean getIsdatatreatment(){
		return (Boolean)this.getParameter("isDataTreatment");
	}

	/**
	 * Return value for input parameter relatedContracts
	 */
	protected List<RelatedContractDTO> getRelatedcontracts(){
		return (List<RelatedContractDTO>)this.getParameter("relatedContracts");
	}

	/**
	 * Return value for input parameter installmentPlan
	 */
	protected PolicyInstallmentPlanDTO getInstallmentplan(){
		return (PolicyInstallmentPlanDTO)this.getParameter("installmentPlan");
	}

	/**
	 * Return value for input parameter hasAcceptedContract
	 */
	protected Boolean getHasacceptedcontract(){
		return (Boolean)this.getParameter("hasAcceptedContract");
	}

	/**
	 * Return value for input parameter inspection
	 */
	protected PolicyInspectionDTO getInspection(){
		return (PolicyInspectionDTO)this.getParameter("inspection");
	}

	/**
	 * Return value for input parameter firstInstallment
	 */
	protected FirstInstallmentDTO getFirstinstallment(){
		return (FirstInstallmentDTO)this.getParameter("firstInstallment");
	}

	/**
	 * Return value for input parameter participants
	 */
	protected List<ParticipantDTO> getParticipants(){
		return (List<ParticipantDTO>)this.getParameter("participants");
	}

	/**
	 * Return value for input parameter businessAgent
	 */
	protected BusinessAgentDTO getBusinessagent(){
		return (BusinessAgentDTO)this.getParameter("businessAgent");
	}

	/**
	 * Return value for input parameter promoter
	 */
	protected PromoterDTO getPromoter(){
		return (PromoterDTO)this.getParameter("promoter");
	}

	/**
	 * Return value for input parameter insuranceCompany
	 */
	protected InsuranceCompanyDTO getInsurancecompany(){
		return (InsuranceCompanyDTO)this.getParameter("insuranceCompany");
	}

	/**
	 * Return value for input parameter bank
	 */
	protected BankDTO getBank(){
		return (BankDTO)this.getParameter("bank");
	}

	/**
	 * Return value for input parameter couponCode
	 */
	protected String getCouponcode(){
		return (String)this.getParameter("couponCode");
	}

	/**
	 * Return value for input parameter identityVerificationCode
	 */
	protected String getIdentityverificationcode(){
		return (String)this.getParameter("identityVerificationCode");
	}

	/**
	 * Set value for String output parameter id
	 */
	protected void setId(final String field){
		this.addParameter("id", field);
	}

	/**
	 * Set value for String output parameter quotationNumber
	 */
	protected void setQuotationnumber(final String field){
		this.addParameter("quotationNumber", field);
	}

	/**
	 * Set value for String output parameter policyNumber
	 */
	protected void setPolicynumber(final String field){
		this.addParameter("policyNumber", field);
	}

	/**
	 * Set value for String output parameter alias
	 */
	protected void setAlias(final String field){
		this.addParameter("alias", field);
	}

	/**
	 * Set value for String output parameter productType
	 */
	protected void setProducttype(final String field){
		this.addParameter("productType", field);
	}

	/**
	 * Set value for ProductDTO output parameter product
	 */
	protected void setProduct(final ProductDTO field){
		this.addParameter("product", field);
	}

	/**
	 * Set value for Boolean output parameter hasAcceptedContract
	 */
	protected void setHasacceptedcontract(final Boolean field){
		this.addParameter("hasAcceptedContract", field);
	}

	/**
	 * Set value for Boolean output parameter isDataTreatment
	 */
	protected void setIsdatatreatment(final Boolean field){
		this.addParameter("isDataTreatment", field);
	}

	/**
	 * Set value for PolicyPaymentMethodDTO output parameter paymentMethod
	 */
	protected void setPaymentmethod(final PolicyPaymentMethodDTO field){
		this.addParameter("paymentMethod", field);
	}

	/**
	 * Set value for List<ParticipantDTO> output parameter participants
	 */
	protected void setParticipants(final List<ParticipantDTO> field){
		this.addParameter("participants", field);
	}

	/**
	 * Set value for FirstInstallmentDTO output parameter firstInstallment
	 */
	protected void setFirstinstallment(final FirstInstallmentDTO field){
		this.addParameter("firstInstallment", field);
	}

	/**
	 * Set value for PromoterDTO output parameter promoter
	 */
	protected void setPromoter(final PromoterDTO field){
		this.addParameter("promoter", field);
	}

	/**
	 * Set value for PolicyInspectionDTO output parameter inspection
	 */
	protected void setInspection(final PolicyInspectionDTO field){
		this.addParameter("inspection", field);
	}

	/**
	 * Set value for List<RelatedContractDTO> output parameter relatedContracts
	 */
	protected void setRelatedcontracts(final List<RelatedContractDTO> field){
		this.addParameter("relatedContracts", field);
	}

	/**
	 * Set value for TotalAmountDTO output parameter totalAmount
	 */
	protected void setTotalamount(final TotalAmountDTO field){
		this.addParameter("totalAmount", field);
	}

	/**
	 * Set value for TotalAmountDTO output parameter totalAmountWithoutTax
	 */
	protected void setTotalamountwithouttax(final TotalAmountDTO field){
		this.addParameter("totalAmountWithoutTax", field);
	}

	/**
	 * Set value for InsuredAmountDTO output parameter insuredAmount
	 */
	protected void setInsuredamount(final InsuredAmountDTO field){
		this.addParameter("insuredAmount", field);
	}

	/**
	 * Set value for PolicyInstallmentPlanDTO output parameter installmentPlan
	 */
	protected void setInstallmentplan(final PolicyInstallmentPlanDTO field){
		this.addParameter("installmentPlan", field);
	}

	/**
	 * Set value for Date output parameter operationDate
	 */
	protected void setOperationdate(final Date field){
		this.addParameter("operationDate", field);
	}

	/**
	 * Set value for QuotationStatusDTO output parameter status
	 */
	protected void setStatus(final QuotationStatusDTO field){
		this.addParameter("status", field);
	}

	/**
	 * Set value for InsuranceCompanyDTO output parameter insuranceCompany
	 */
	protected void setInsurancecompany(final InsuranceCompanyDTO field){
		this.addParameter("insuranceCompany", field);
	}

	/**
	 * Set value for PaymentConfigurationDTO output parameter paymentConfiguration
	 */
	protected void setPaymentconfiguration(final PaymentConfigurationDTO field){
		this.addParameter("paymentConfiguration", field);
	}

	/**
	 * Set value for HolderDTO output parameter holder
	 */
	protected void setHolder(final HolderDTO field){
		this.addParameter("holder", field);
	}

	/**
	 * Set value for Date output parameter cancelationDate
	 */
	protected void setCancelationdate(final Date field){
		this.addParameter("cancelationDate", field);
	}

	/**
	 * Set value for ValidityPeriodDTO output parameter validityPeriod
	 */
	protected void setValidityperiod(final ValidityPeriodDTO field){
		this.addParameter("validityPeriod", field);
	}

	/**
	 * Set value for CurrentInstallmentDTO output parameter currentInstallment
	 */
	protected void setCurrentinstallment(final CurrentInstallmentDTO field){
		this.addParameter("currentInstallment", field);
	}

	/**
	 * Set value for TotalAmountDTO output parameter premiumDebt
	 */
	protected void setPremiumdebt(final TotalAmountDTO field){
		this.addParameter("premiumDebt", field);
	}

	/**
	 * Set value for RenewalPolicyDTO output parameter renewalPolicy
	 */
	protected void setRenewalpolicy(final RenewalPolicyDTO field){
		this.addParameter("renewalPolicy", field);
	}

	/**
	 * Set value for String output parameter certificateNumber
	 */
	protected void setCertificatenumber(final String field){
		this.addParameter("certificateNumber", field);
	}

	/**
	 * Set value for String output parameter subscriptionType
	 */
	protected void setSubscriptiontype(final String field){
		this.addParameter("subscriptionType", field);
	}

	/**
	 * Set value for BusinessAgentDTO output parameter businessAgent
	 */
	protected void setBusinessagent(final BusinessAgentDTO field){
		this.addParameter("businessAgent", field);
	}

	/**
	 * Set value for BankDTO output parameter bank
	 */
	protected void setBank(final BankDTO field){
		this.addParameter("bank", field);
	}

	/**
	 * Set value for InstallmentPlansDTO output parameter lastInstallment
	 */
	protected void setLastinstallment(final InstallmentPlansDTO field){
		this.addParameter("lastInstallment", field);
	}

	/**
	 * Set value for Date output parameter externalDocumentationSendDate
	 */
	protected void setExternaldocumentationsenddate(final Date field){
		this.addParameter("externalDocumentationSendDate", field);
	}

	/**
	 * Set value for NonRenewalPolicyDTO output parameter nonRenewalPolicy
	 */
	protected void setNonrenewalpolicy(final NonRenewalPolicyDTO field){
		this.addParameter("nonRenewalPolicy", field);
	}

	/**
	 * Set value for String output parameter couponCode
	 */
	protected void setCouponcode(final String field){
		this.addParameter("couponCode", field);
	}
}
