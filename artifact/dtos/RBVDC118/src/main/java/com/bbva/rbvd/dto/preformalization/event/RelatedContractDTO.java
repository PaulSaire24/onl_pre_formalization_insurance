package com.bbva.rbvd.dto.preformalization.event;

import com.bbva.apx.dto.AbstractDTO;

public class RelatedContractDTO extends AbstractDTO {
    private String number;
    private String contractId;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    @Override
    public String toString() {
        return "RelatedContractDTO{" +
                "number='" + number + '\'' +
                ", contractId='" + contractId + '\'' +
                '}';
    }
}
