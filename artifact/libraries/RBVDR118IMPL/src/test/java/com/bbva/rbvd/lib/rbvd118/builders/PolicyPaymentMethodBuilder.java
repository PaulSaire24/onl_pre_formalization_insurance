package com.bbva.rbvd.lib.rbvd118.builders;

import com.bbva.rbvd.dto.insrncsale.policy.PolicyPaymentMethodDTO;

public class PolicyPaymentMethodBuilder {

    private final PolicyPaymentMethodDTO policyPaymentMethodDTO;

    public PolicyPaymentMethodBuilder() {
        policyPaymentMethodDTO = new PolicyPaymentMethodDTO();
    }

    public static PolicyPaymentMethodBuilder instance() {
        return new PolicyPaymentMethodBuilder();
    }

    public PolicyPaymentMethodBuilder withPaymentType(String paymentType) {
        policyPaymentMethodDTO.setPaymentType(paymentType);
        return this;
    }

    public PolicyPaymentMethodBuilder withInstallmentFrequency(String installmentFrequency) {
        policyPaymentMethodDTO.setInstallmentFrequency(installmentFrequency);
        return this;
    }

    public PolicyPaymentMethodDTO build() {
        return policyPaymentMethodDTO;
    }
}
