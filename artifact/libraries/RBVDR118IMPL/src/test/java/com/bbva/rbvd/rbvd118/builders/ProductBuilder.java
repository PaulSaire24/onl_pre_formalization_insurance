package com.bbva.rbvd.rbvd118.builders;

import com.bbva.rbvd.dto.preformalization.Product;
import com.bbva.rbvd.dto.preformalization.ProductPlan;

public class ProductBuilder {
    private final Product product;

    private ProductBuilder() {
        product = new Product();
    }

    public static ProductBuilder instance() {
        return new ProductBuilder();
    }

    public ProductBuilder withId(String id) {
        product.setId(id);
        return this;
    }

    public ProductBuilder withPlan(String planId) {
        ProductPlan plan = new ProductPlan();
        product.setPlan(plan);
        return this;
    }

    public Product build() {
        return product;
    }
}
