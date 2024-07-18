package com.bbva.rbvd.dto.preformalization.event;

import com.bbva.apx.dto.AbstractDTO;

public class ContactDetailDTO extends AbstractDTO {
    private ContactDTO contact;

    public ContactDTO getContact() {
        return contact;
    }

    public void setContact(ContactDTO contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "ContactDetailDTO{" +
                "contact=" + contact +
                '}';
    }
}
