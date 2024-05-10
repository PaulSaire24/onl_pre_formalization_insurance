package com.bbva.rbvd.lib.rbvd118.builders;

import com.bbva.rbvd.dto.insrncsale.commons.ValidityPeriodDTO;
import com.bbva.rbvd.dto.preformalization.ValidityPeriod;

import java.util.Date;

public class ValidityPeriodBuilder {

    private final ValidityPeriodDTO validityPeriod;

    private ValidityPeriodBuilder() {
        this.validityPeriod = new ValidityPeriodDTO();
    }

    public static ValidityPeriodBuilder instance() {
        return new ValidityPeriodBuilder();
    }

    public ValidityPeriodBuilder withStartDate(Date startDate) {
        this.validityPeriod.setStartDate(startDate);
        return this;
    }

    public ValidityPeriodBuilder withEndDate(Date endDate) {
        this.validityPeriod.setEndDate(endDate);
        return this;
    }

    public ValidityPeriodDTO build() {
        return this.validityPeriod;
    }
}
