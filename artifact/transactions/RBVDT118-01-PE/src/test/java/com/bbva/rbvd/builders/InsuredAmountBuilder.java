package com.bbva.rbvd.builders;

import com.bbva.rbvd.dto.insrncsale.policy.InsuredAmountDTO;

public class InsuredAmountBuilder {
    private final InsuredAmountDTO insuredAmountDTO;

    public InsuredAmountBuilder() {
        insuredAmountDTO = new InsuredAmountDTO();
    }

    public static InsuredAmountBuilder instance() {
        return new InsuredAmountBuilder();
    }

    public InsuredAmountBuilder withAmount(Double amount) {
        insuredAmountDTO.setAmount(amount);
        return this;
    }

    public InsuredAmountBuilder withCurrency(String currency) {
        insuredAmountDTO.setCurrency(currency);
        return this;
    }

    public InsuredAmountDTO build() {
        return insuredAmountDTO;
    }
}
