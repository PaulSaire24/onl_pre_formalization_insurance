package com.bbva.rbvd.lib.r415.impl.pattern.impl;

import com.bbva.rbvd.lib.r415.impl.pattern.PostInsuranceProduct;
import com.bbva.rbvd.lib.r415.impl.pattern.PreFormalizationInsurance;
import com.bbva.rbvd.lib.r415.impl.pattern.PreInsuranceProduct;

public abstract class PreFormalizationDecorator implements PreFormalizationInsurance {

    private final PreInsuranceProduct preInsuranceProduct;
    private final PostInsuranceProduct postInsuranceProduct;

    protected PreFormalizationDecorator(PreInsuranceProduct preInsuranceProduct, PostInsuranceProduct postInsuranceProduct) {
        this.preInsuranceProduct = preInsuranceProduct;
        this.postInsuranceProduct = postInsuranceProduct;
    }

    public PreInsuranceProduct getPreInsuranceProduct() {
        return preInsuranceProduct;
    }

    public PostInsuranceProduct getPostInsuranceProduct() {
        return postInsuranceProduct;
    }
}
