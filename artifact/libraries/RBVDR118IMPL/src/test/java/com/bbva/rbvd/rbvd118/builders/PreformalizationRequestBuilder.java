package com.bbva.rbvd.rbvd118.builders;

import com.bbva.rbvd.dto.insrncsale.commons.BankDTO;
import com.bbva.rbvd.dto.insrncsale.policy.*;
import com.bbva.rbvd.dto.preformalization.PreformalizationDTO;
import com.bbva.rbvd.dto.preformalization.Product;
import com.bbva.rbvd.dto.preformalization.ValidityPeriod;

import java.util.ArrayList;

public class PreformalizationRequestBuilder {
    private final PreformalizationDTO preformalizationDTO;

    public PreformalizationRequestBuilder() {
        preformalizationDTO = new PreformalizationDTO();
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

    public PreformalizationDTO build() {
        return preformalizationDTO;
    }
}
