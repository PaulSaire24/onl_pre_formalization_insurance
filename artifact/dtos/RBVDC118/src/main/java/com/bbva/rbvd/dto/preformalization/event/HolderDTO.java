package com.bbva.rbvd.dto.preformalization.event;

import com.bbva.apx.dto.AbstractDTO;

import java.util.List;

public class HolderDTO extends AbstractDTO {
    private String id;
    private IdentityDocumentDTO identityDocument;
    private  List<ContactDetailDTO> contactDetails;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IdentityDocumentDTO getIdentityDocument() {
        return identityDocument;
    }

    public void setIdentityDocument(IdentityDocumentDTO identityDocument) {
        this.identityDocument = identityDocument;
    }

    public List<ContactDetailDTO> getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(List<ContactDetailDTO> contactDetails) {
        this.contactDetails = contactDetails;
    }

    @Override
    public String toString() {
        return "HolderDTO{" +
                "id='" + id + '\'' +
                ", identityDocument=" + identityDocument +
                ", contactDetails=" + contactDetails +
                '}';
    }
}
