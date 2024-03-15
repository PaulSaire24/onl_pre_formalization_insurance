package com.bbva.rbvd.dto.preformalization;

import com.bbva.rbvd.dto.insrncsale.commons.CommonFieldsDTO;

public class PaymentConfiguration extends CommonFieldsDTO {
    private PaymentFrequency frequency;
    private String paymentType;
    private String nextPaymentDate;
    private Boolean isPaymentPending;

    public PaymentFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(PaymentFrequency frequency) {
        this.frequency = frequency;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getNextPaymentDate() {
        return nextPaymentDate;
    }

    public void setNextPaymentDate(String nextPaymentDate) {
        this.nextPaymentDate = nextPaymentDate;
    }

    public Boolean getPaymentPending() {
        return isPaymentPending;
    }

    public void setPaymentPending(Boolean paymentPending) {
        isPaymentPending = paymentPending;
    }

    @Override
    public String toString() {
        return "PaymentConfiguration{" +
                "frequency=" + frequency +
                ", paymentType='" + paymentType + '\'' +
                ", nextPaymentDate='" + nextPaymentDate + '\'' +
                ", isPaymentPending=" + isPaymentPending +
                '}';
    }
}
