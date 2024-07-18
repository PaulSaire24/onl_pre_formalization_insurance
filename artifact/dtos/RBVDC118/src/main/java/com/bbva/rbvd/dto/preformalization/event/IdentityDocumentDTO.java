package com.bbva.rbvd.dto.preformalization.event;

import com.bbva.apx.dto.AbstractDTO;

public class IdentityDocumentDTO extends AbstractDTO {
    private String documentNumber;
    private DocumentTypeDTO documentType;

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public DocumentTypeDTO getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentTypeDTO documentType) {
        this.documentType = documentType;
    }

    @Override
    public String toString() {
        return "IdentityDocumentDTO{" +
                "documentNumber='" + documentNumber + '\'' +
                ", documentType=" + documentType +
                '}';
    }
}
