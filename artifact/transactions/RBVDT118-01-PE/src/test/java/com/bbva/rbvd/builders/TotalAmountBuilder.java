package com.bbva.rbvd.builders;

import com.bbva.rbvd.dto.insrncsale.policy.TotalAmountDTO;

public class TotalAmountBuilder {
    private final TotalAmountDTO totalAmountDTO;

    public TotalAmountBuilder() {
        totalAmountDTO = new TotalAmountDTO();
    }

    public static TotalAmountBuilder instance() {
        return new TotalAmountBuilder();
    }

    public TotalAmountBuilder withAmount(Double amount) {
        totalAmountDTO.setAmount(amount);
        return this;
    }

    public TotalAmountDTO build() {
        return totalAmountDTO;
    }
}
