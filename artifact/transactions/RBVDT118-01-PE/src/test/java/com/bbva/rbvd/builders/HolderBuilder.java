package com.bbva.rbvd.builders;

import com.bbva.rbvd.dto.insrncsale.commons.HolderDTO;
import com.bbva.rbvd.dto.insrncsale.commons.IdentityDocumentDTO;

public class HolderBuilder {
    private final HolderDTO holderDTO;

    private HolderBuilder() {
        holderDTO = new HolderDTO();
    }

    public static HolderBuilder instance() {
        return new HolderBuilder();
    }

    public HolderBuilder withId(String id) {
        holderDTO.setId(id);
        return this;
    }

    public HolderBuilder withIdentityDocument(IdentityDocumentDTO identityDocument) {
        holderDTO.setIdentityDocument(identityDocument);
        return this;
    }

    public HolderDTO build() {
        return holderDTO;
    }
}
