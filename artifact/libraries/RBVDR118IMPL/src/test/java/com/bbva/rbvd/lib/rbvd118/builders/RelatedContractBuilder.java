package com.bbva.rbvd.lib.rbvd118.builders;

import com.bbva.rbvd.dto.insrncsale.policy.ContractDetailsDTO;
import com.bbva.rbvd.dto.insrncsale.policy.RelatedContractDTO;
import com.bbva.rbvd.dto.insrncsale.policy.RelationTypeDTO;
import com.bbva.rbvd.dto.preformalization.ContractDetails;
import com.bbva.rbvd.dto.preformalization.RelatedContract;

public class RelatedContractBuilder {
    private final RelatedContractDTO relatedContract;

    public RelatedContractBuilder() {
        relatedContract = new RelatedContractDTO();
    }

    public static RelatedContractBuilder instance() {
        return new RelatedContractBuilder();
    }

    public RelatedContractBuilder withRelationType(String relationTypeId) {
        RelationTypeDTO relationType = new RelationTypeDTO();
        relationType.setId(relationTypeId);
        relatedContract.setRelationType(relationType);
        return this;
    }

    public RelatedContractBuilder withContractDetails(ContractDetailsDTO contractDetails) {
        relatedContract.setContractDetails(contractDetails);
        return this;
    }

    public RelatedContractDTO build() {
        return relatedContract;
    }
}
