package com.bbva.rbvd.lib.rbvd118.builders;

import com.bbva.rbvd.dto.insrncsale.commons.BankDTO;
import com.bbva.rbvd.dto.insrncsale.commons.HolderDTO;
import com.bbva.rbvd.dto.insrncsale.policy.*;
import com.bbva.rbvd.dto.preformalization.*;
import com.bbva.rbvd.dto.preformalization.dto.InsuranceDTO;

import java.util.ArrayList;

public class PreformalizationRequestBuilder {
    private final InsuranceDTO preformalizationDTO;

    public PreformalizationRequestBuilder() {
        preformalizationDTO = new InsuranceDTO();
        preformalizationDTO.setParticipants(new ArrayList<>());
        preformalizationDTO.setRelatedContracts(new ArrayList<>());
    }

    public static PreformalizationRequestBuilder instance() {
        return new PreformalizationRequestBuilder();
    }

    public PreformalizationRequestBuilder withPolicyNumber(String policyNumber) {
        preformalizationDTO.setPolicyNumber(policyNumber);
        preformalizationDTO.setParticipants(new ArrayList<>());
        return this;
    }

    public PreformalizationRequestBuilder withProduct(Product product) {
        preformalizationDTO.setProduct(product);
        return this;
    }

    public PreformalizationRequestBuilder withValidityPeriod(ValidityPeriod validityPeriod) {
        preformalizationDTO.setInsuranceValidityPeriod(validityPeriod);
        return this;
    }

    public PreformalizationRequestBuilder withFirstInstallment(FirstInstallmentDTO firstInstallment) {
        preformalizationDTO.setFirstInstallment(firstInstallment);
        return this;
    }

    public PreformalizationRequestBuilder withInstallmentPlan(PolicyInstallmentPlanDTO installmentPlan) {
        preformalizationDTO.setInstallmentPlan(installmentPlan);
        return this;
    }

    public PreformalizationRequestBuilder withTotalAmount(TotalAmountDTO totalAmount) {
        preformalizationDTO.setTotalAmount(totalAmount);
        return this;
    }

    public PreformalizationRequestBuilder withInsuredAmount(InsuredAmountDTO insuredAmount) {
        preformalizationDTO.setInsuredAmount(insuredAmount);
        return this;
    }

    public PreformalizationRequestBuilder withBusinessAgent(String businessAgentId) {
        BusinessAgentDTO businessAgent = new BusinessAgentDTO();
        businessAgent.setId(businessAgentId);
        preformalizationDTO.setBusinessAgent(businessAgent);
        return this;
    }

    public PreformalizationRequestBuilder withPromoter(String promoterId) {
        PromoterDTO promoter = new PromoterDTO();
        promoter.setId(promoterId);
        preformalizationDTO.setPromoter(promoter);
        return this;
    }

    public PreformalizationRequestBuilder withBank(BankDTO bank) {
        preformalizationDTO.setBank(bank);
        return this;
    }

    public PreformalizationRequestBuilder withInsuranceCompany(String insuranceCompanyId) {
        InsuranceCompanyDTO insuranceCompany = new InsuranceCompanyDTO();
        insuranceCompany.setId(insuranceCompanyId);
        preformalizationDTO.setInsuranceCompany(insuranceCompany);
        return this;
    }

    public PreformalizationRequestBuilder withQuotationId(String quotationId) {
        preformalizationDTO.setQuotationId(quotationId);
        return this;
    }

    public PreformalizationRequestBuilder addParticipant(ParticipantDTO participant) {
        preformalizationDTO.getParticipants().add(participant);
        return this;
    }

    public PreformalizationRequestBuilder withPaymentMethod(PolicyPaymentMethodDTO paymentMethod) {
        preformalizationDTO.setPaymentMethod(paymentMethod);
        return this;
    }

    public PreformalizationRequestBuilder addRelatedContract(RelatedContract relatedContract) {
        preformalizationDTO.getRelatedContracts().add(relatedContract);
        return this;
    }

    public PreformalizationRequestBuilder withHolder(HolderDTO holder) {
        preformalizationDTO.setHolder(holder);
        return this;
    }

    public InsuranceDTO build() {
        return preformalizationDTO;
    }
}
