package com.bbva.rbvd.dto.preformalization.input;

import com.bbva.rbvd.dto.insrncsale.commons.CommonFieldsDTO;

public class PlanType extends CommonFieldsDTO {

    @Override
    public String toString() {
        String sb = "PlanType{" + "id='" + this.getId() + '\'' +
                "name=" + this.getName() + '\''
                + '}';
        return sb;
    }
}
