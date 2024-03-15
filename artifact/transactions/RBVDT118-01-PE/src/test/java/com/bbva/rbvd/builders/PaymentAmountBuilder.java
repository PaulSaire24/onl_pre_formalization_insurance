package com.bbva.rbvd.builders;

import com.bbva.rbvd.dto.insrncsale.commons.PaymentAmountDTO;

public class PaymentAmountBuilder {
    private final PaymentAmountDTO paymentAmountDTO;

    public PaymentAmountBuilder() {
        paymentAmountDTO = new PaymentAmountDTO();
    }

    public static PaymentAmountBuilder instance() {
        return new PaymentAmountBuilder();
    }

    public PaymentAmountBuilder withAmount(Double paymentAmount) {
        paymentAmountDTO.setAmount(paymentAmount);
        return this;
    }

    public PaymentAmountBuilder withCurrency(String paymentCurrency) {
        paymentAmountDTO.setCurrency(paymentCurrency);
        return this;
    }

    public PaymentAmountDTO build() {
        return paymentAmountDTO;
    }
}
