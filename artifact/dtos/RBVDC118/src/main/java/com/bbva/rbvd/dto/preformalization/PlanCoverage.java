package com.bbva.rbvd.dto.preformalization;

import com.bbva.rbvd.dto.insrncsale.commons.CommonFieldsDTO;

public class PlanCoverage extends CommonFieldsDTO {

    private InsuranceBranch insuranceBranch;


    public InsuranceBranch getInsuranceBranch() {
        return insuranceBranch;
    }

    public void setInsuranceBranch(InsuranceBranch insuranceBranch) {
        this.insuranceBranch = insuranceBranch;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PlanCoverage{");
        sb.append("id='").append(this.getId()).append('\'');
        sb.append("name='").append(this.getName()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
