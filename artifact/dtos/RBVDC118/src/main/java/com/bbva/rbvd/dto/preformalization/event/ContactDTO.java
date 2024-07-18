package com.bbva.rbvd.dto.preformalization.event;

import com.bbva.apx.dto.AbstractDTO;

public class ContactDTO extends AbstractDTO {
    private String contactType;
    private String value;

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ContactDTO{" +
                "contactType='" + contactType + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
