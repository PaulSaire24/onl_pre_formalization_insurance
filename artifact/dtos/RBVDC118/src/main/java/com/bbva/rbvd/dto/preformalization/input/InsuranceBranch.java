package com.bbva.rbvd.dto.preformalization.input;

import com.bbva.rbvd.dto.insrncsale.commons.CommonFieldsDTO;

public class InsuranceBranch extends CommonFieldsDTO {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("InsuranceBranch{");
        sb.append("id='").append(this.getId()).append('\'');
        sb.append("name='").append(this.getName()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
