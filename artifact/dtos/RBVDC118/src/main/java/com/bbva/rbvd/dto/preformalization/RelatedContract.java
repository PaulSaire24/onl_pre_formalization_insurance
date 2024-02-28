package com.bbva.rbvd.dto.preformalization;

import com.bbva.rbvd.dto.insrncsale.commons.CommonFieldsDTO;
import com.bbva.rbvd.dto.insrncsale.policy.RelationTypeDTO;

public class RelatedContract extends CommonFieldsDTO {
    private RelationTypeDTO relationType;
    private ContractDetails contractDetails;

    public RelationTypeDTO getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationTypeDTO relationType) {
        this.relationType = relationType;
    }

    public ContractDetails getContractDetails() {
        return contractDetails;
    }

    public void setContractDetails(ContractDetails contractDetails) {
        this.contractDetails = contractDetails;
    }

    @Override
    public String toString() {
        return "RelatedContract{" +
                "relationType=" + relationType +
                ", contractDetails=" + contractDetails +
                '}';
    }
}
