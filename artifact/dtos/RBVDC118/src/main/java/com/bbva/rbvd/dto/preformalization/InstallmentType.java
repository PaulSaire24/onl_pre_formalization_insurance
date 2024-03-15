package com.bbva.rbvd.dto.preformalization;

import com.bbva.rbvd.dto.insrncsale.commons.CommonFieldsDTO;

public class InstallmentType extends CommonFieldsDTO {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("InstallmentType{");
        sb.append("id=").append(this.getId()).append(",");
        sb.append("description=").append(this.getDescription()).append(",");
        sb.append("}");
        return sb.toString();
    }
}
