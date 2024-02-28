package com.bbva.rbvd.dto.preformalization.input;

import com.bbva.rbvd.dto.insrncsale.commons.CommonFieldsDTO;

public class PaymentFrequency extends CommonFieldsDTO {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentFrequency {");
        sb.append("id=").append(this.getId()).append(", ");
        sb.append("name=").append(this.getName()).append(", ");
        sb.append("}");
        return sb.toString();
    }
}
