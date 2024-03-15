package com.bbva.rbvd.builders;

import com.bbva.rbvd.dto.insrncsale.policy.RelationTypeDTO;
import com.bbva.rbvd.dto.preformalization.ContractDetails;
import com.bbva.rbvd.dto.preformalization.RelatedContract;

public class RelatedContractBuilder {
    private final RelatedContract relatedContract;

    public RelatedContractBuilder() {
        relatedContract = new RelatedContract();
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

    public RelatedContractBuilder withContractDetails(ContractDetails contractDetails) {
        relatedContract.setContractDetails(contractDetails);
        return this;
    }

    public RelatedContract build() {
        return relatedContract;
    }
}
