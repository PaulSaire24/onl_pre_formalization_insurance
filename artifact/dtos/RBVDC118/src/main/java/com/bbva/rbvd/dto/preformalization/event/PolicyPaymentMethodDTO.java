package com.bbva.rbvd.dto.preformalization.event;

import com.bbva.apx.dto.AbstractDTO;

import java.util.List;

public class PolicyPaymentMethodDTO extends AbstractDTO {
    private String paymentType;
    private List<RelatedContractDTO> relatedContracts;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public List<RelatedContractDTO> getRelatedContracts() {
        return relatedContracts;
    }

    public void setRelatedContracts(List<RelatedContractDTO> relatedContracts) {
        this.relatedContracts = relatedContracts;
    }

    @Override
    public String toString() {
        return "PolicyPaymentMethodDTO{" +
                "paymentType='" + paymentType + '\'' +
                ", relatedContracts=" + relatedContracts +
                '}';
    }
}
