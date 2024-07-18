package com.bbva.rbvd.dto.preformalization.event;

import java.util.Calendar;

public class InsurancePreFormalizedDTO {
    private String quotationId;
    private Calendar operationDate;
    private ValidityPeriodDTO validityPeriod;
    private HolderDTO holder;
    private ProductCreatedInsrcEventDTO product;
    private PolicyPaymentMethodDTO paymentMethod;
    private StatusDTO status;
    private String contractId;
    private String aap;

    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

    public Calendar getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Calendar operationDate) {
        this.operationDate = operationDate;
    }

    public ValidityPeriodDTO getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(ValidityPeriodDTO validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public HolderDTO getHolder() {
        return holder;
    }

    public void setHolder(HolderDTO holder) {
        this.holder = holder;
    }

    public ProductCreatedInsrcEventDTO getProduct() {
        return product;
    }

    public void setProduct(ProductCreatedInsrcEventDTO product) {
        this.product = product;
    }

    public PolicyPaymentMethodDTO getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PolicyPaymentMethodDTO paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getAap() {
        return aap;
    }

    public void setAap(String aap) {
        this.aap = aap;
    }

    @Override
    public String toString() {
        return "CreatedInsuranceDTO{" +
                "quotationId='" + quotationId + '\'' +
                ", operationDate=" + operationDate +
                ", validityPeriod=" + validityPeriod +
                ", holder=" + holder +
                ", product=" + product +
                ", paymentMethod=" + paymentMethod +
                ", status=" + status +
                ", contractId='" + contractId + '\'' +
                ", aap='" + aap + '\'' +
                '}';
    }
}
