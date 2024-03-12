package com.bbva.rbvd.rbvd118.builders;

import com.bbva.rbvd.dto.insrncsale.policy.FirstInstallmentDTO;

public class FirstInstallmentBuilder {
    private final FirstInstallmentDTO firstInstallment;

    public FirstInstallmentBuilder() {
        this.firstInstallment = new FirstInstallmentDTO();
    }

    public static FirstInstallmentBuilder instance() {
        return new FirstInstallmentBuilder();
    }

    public FirstInstallmentBuilder withIsPaymentRequired(boolean isPaymentRequired) {
        this.firstInstallment.setIsPaymentRequired(isPaymentRequired);
        return this;
    }

    public FirstInstallmentDTO build() {
        return this.firstInstallment;
    }
}
