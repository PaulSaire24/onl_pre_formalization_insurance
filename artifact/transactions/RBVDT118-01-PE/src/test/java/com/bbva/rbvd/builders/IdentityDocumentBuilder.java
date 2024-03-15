package com.bbva.rbvd.builders;

import com.bbva.rbvd.dto.insrncsale.commons.DocumentTypeDTO;
import com.bbva.rbvd.dto.insrncsale.commons.IdentityDocumentDTO;

public class IdentityDocumentBuilder {

    private final IdentityDocumentDTO identityDocumentDTO;

    public IdentityDocumentBuilder() {
        identityDocumentDTO = new IdentityDocumentDTO();
    }

    public static IdentityDocumentBuilder instance() {
        return new IdentityDocumentBuilder();
    }

    public IdentityDocumentBuilder withDocumentType(String documentType) {
        DocumentTypeDTO documentTypeDTO = new DocumentTypeDTO();
        documentTypeDTO.setId(documentType);
        identityDocumentDTO.setDocumentType(documentTypeDTO);
        return this;
    }

    public IdentityDocumentBuilder withDocumentNumber(String documentNumber) {
        identityDocumentDTO.setDocumentNumber(documentNumber);
        return this;
    }

    public IdentityDocumentDTO build() {
        return identityDocumentDTO;
    }
}
