package com.bbva.rbvd.lib.rbvd118.builders;

import com.bbva.rbvd.dto.insrncsale.policy.PlanDTO;
import com.bbva.rbvd.dto.insrncsale.policy.ProductDTO;
import com.bbva.rbvd.dto.preformalization.Product;
import com.bbva.rbvd.dto.preformalization.ProductPlan;

public class ProductBuilder {
    private final ProductDTO product;

    private ProductBuilder() {
        product = new ProductDTO();
    }

    public static ProductBuilder instance() {
        return new ProductBuilder();
    }

    public ProductBuilder withId(String id) {
        product.setId(id);
        return this;
    }

    public ProductBuilder withPlan(String planId) {
        PlanDTO plan = new PlanDTO();
        product.setPlan(plan);
        return this;
    }

    public ProductDTO build() {
        return product;
    }
}
