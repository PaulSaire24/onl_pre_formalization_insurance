package com.bbva.rbvd.dto.preformalization;

import com.bbva.rbvd.dto.insrncsale.commons.CommonFieldsDTO;

import java.util.List;

public class ProductPlan extends CommonFieldsDTO {

    public PlanType planType;

    public List<PlanCoverage> coverage;

    public PlanType getPlanType() {
        return planType;
    }

    public void setPlanType(PlanType planType) {
        this.planType = planType;
    }

    public List<PlanCoverage> getCoverage() {
        return coverage;
    }

    public void setCoverage(List<PlanCoverage> coverage) {
        this.coverage = coverage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ProductPlan {");
        sb.append("id=").append(getId()).append(", ");
        sb.append("description=").append(getDescription()).append(", ");
        sb.append("planType=").append(planType.toString()).append(", ");
        sb.append("coverage=").append(coverage.toString());
        sb.append('}');
        return sb.toString();
    }
}
