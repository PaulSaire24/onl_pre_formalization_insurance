package com.bbva.rbvd.dto.preformalization.input;

import com.bbva.rbvd.dto.insrncsale.commons.CommonFieldsDTO;

public class ValidityPeriod extends CommonFieldsDTO {
    private String unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "ValidityPeriod{" +
                "unit='" + unit + '\'' +
                "description='" + getDescription() + '\'' +
                "startDate='" + getStartDate() + '\'' +
                "endDate='" + getEndDate() + '\'' +
                '}';
    }
}
