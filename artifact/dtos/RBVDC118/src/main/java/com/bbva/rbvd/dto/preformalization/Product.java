package com.bbva.rbvd.dto.preformalization;


import com.bbva.rbvd.dto.insrncsale.commons.CommonFieldsDTO;

/**
 * The ProductDto class...
 */
public class Product extends CommonFieldsDTO {

    private ProductPlan plan;

    public ProductPlan getPlan() {
        return plan;
    }

    public void setPlan(ProductPlan plan) {
        this.plan = plan;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Product{");
        sb.append("id=").append(getId()).append(", ");
        sb.append("name=").append(getName()).append(", ");
        sb.append("description=").append(getDescription()).append(", ");
        sb.append("plan=").append(plan.toString());
        sb.append('}');
        return sb.toString();
    }
}
