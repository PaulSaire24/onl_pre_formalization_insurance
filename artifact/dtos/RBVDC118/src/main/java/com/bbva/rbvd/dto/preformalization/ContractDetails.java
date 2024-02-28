package com.bbva.rbvd.dto.preformalization;

import com.bbva.rbvd.dto.insrncsale.commons.CommonFieldsDTO;
import com.bbva.rbvd.dto.insrncsale.policy.NumberTypeDTO;

public class ContractDetails extends CommonFieldsDTO {
    private String contractType;
    private NumberTypeDTO numberType;
    private ProductType productType;

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public NumberTypeDTO getNumberType() {
        return numberType;
    }

    public void setNumberType(NumberTypeDTO numberType) {
        this.numberType = numberType;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    @Override
    public String toString() {
        return "ContractDetails{" +
                "contractType='" + contractType + '\'' +
                ", numberType=" + numberType +
                ", productType=" + productType +
                '}';
    }
}
