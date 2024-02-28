package com.bbva.rbvd.dto.preformalization.input;

import com.bbva.rbvd.dto.insrncsale.commons.CommonFieldsDTO;

public class InsuranceStatus extends CommonFieldsDTO {

    private Reason reason;

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "InsuranceStatus{" +
                "id=" + this.getId() +
                ",description=" + this.getDescription() +
                ",reason=" + reason +
                '}';
    }
}
