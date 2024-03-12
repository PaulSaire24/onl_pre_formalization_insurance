package com.bbva.rbvd.rbvd118.builders;

import com.bbva.rbvd.dto.preformalization.ValidityPeriod;

import java.util.Date;

public class ValidityPeriodBuilder {

    private final ValidityPeriod validityPeriod;

    private ValidityPeriodBuilder() {
        this.validityPeriod = new ValidityPeriod();
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

    public ValidityPeriod build() {
        return this.validityPeriod;
    }
}
