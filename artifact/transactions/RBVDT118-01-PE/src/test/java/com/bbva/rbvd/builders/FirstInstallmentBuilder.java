package com.bbva.rbvd.builders;

import com.bbva.rbvd.dto.insrncsale.commons.PaymentAmountDTO;
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

    public FirstInstallmentBuilder withPaymentAmount(PaymentAmountDTO paymentAmount) {
        this.firstInstallment.setPaymentAmount(paymentAmount);
        return this;
    }

    public FirstInstallmentDTO build() {
        return this.firstInstallment;
    }
}
