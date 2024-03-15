package com.bbva.rbvd.builders;

import com.bbva.rbvd.dto.insrncsale.policy.NumberTypeDTO;
import com.bbva.rbvd.dto.preformalization.ContractDetails;
import com.bbva.rbvd.dto.preformalization.ProductType;

public class ContractDetailsBuilder {
    private final ContractDetails contractDetails;

    public ContractDetailsBuilder() {
        contractDetails = new ContractDetails();
    }

    public static ContractDetailsBuilder instance() {
        return new ContractDetailsBuilder();
    }

    public ContractDetailsBuilder withContractType(String contractType) {
        contractDetails.setContractType(contractType);
        return this;
    }

    public ContractDetailsBuilder withContractId(String contractId) {
        contractDetails.setContractId(contractId);
        return this;
    }

    public ContractDetailsBuilder withNumber(String number) {
        contractDetails.setNumber(number);
        return this;
    }

    public ContractDetailsBuilder withNumberType(String numberTypeId) {
        NumberTypeDTO numberType = new NumberTypeDTO();
        numberType.setId(numberTypeId);
        contractDetails.setNumberType(numberType);
        return this;
    }

    public ContractDetailsBuilder withProductType(String productTypeId) {
        ProductType productType = new ProductType();
        productType.setId(productTypeId);
        contractDetails.setProductType(productType);
        return this;
    }

    public ContractDetails build() {
        return contractDetails;
    }
}
