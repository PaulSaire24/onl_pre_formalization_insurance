package com.bbva.rbvd.dto.preformalization;

import com.bbva.rbvd.dto.insrncsale.commons.CommonFieldsDTO;

public class Reason extends CommonFieldsDTO {

    private CommonFieldsDTO category;

    public CommonFieldsDTO getCategory() {
        return category;
    }

    public void setCategory(CommonFieldsDTO category) {
        this.category = category;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Reason{");
        sb.append("id=").append(this.getId());
        sb.append(", description=").append(this.getDescription());
        sb.append("}");
        return sb.toString();
    }
}
