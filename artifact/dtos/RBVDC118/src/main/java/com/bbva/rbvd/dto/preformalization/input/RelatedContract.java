package com.bbva.rbvd.dto.preformalization.input;

import com.bbva.rbvd.dto.insrncsale.commons.CommonFieldsDTO;
import com.bbva.rbvd.dto.insrncsale.policy.RelationTypeDTO;

public class RelatedContract extends CommonFieldsDTO {
    private RelationTypeDTO relationType;
    private ContractDetails contractDetails;

    @Override
    public String toString() {
        return "RelatedContract{" +
                "relationType=" + relationType +
                ", contractDetails=" + contractDetails +
                '}';
    }
}
