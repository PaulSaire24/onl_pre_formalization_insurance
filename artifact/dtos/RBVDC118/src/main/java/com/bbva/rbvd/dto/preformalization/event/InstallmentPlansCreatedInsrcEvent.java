package com.bbva.rbvd.dto.preformalization.event;

import com.bbva.apx.dto.AbstractDTO;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"type"})
public class InstallmentPlansCreatedInsrcEvent extends AbstractDTO {
    private Integer paymentsTotalNumber;
    private PaymentAmountDTO paymentAmount;
    private PaymentPeriodDTO period;

    public Integer getPaymentsTotalNumber() {
        return paymentsTotalNumber;
    }

    public void setPaymentsTotalNumber(Integer paymentsTotalNumber) {
        this.paymentsTotalNumber = paymentsTotalNumber;
    }

    public PaymentAmountDTO getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(PaymentAmountDTO paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public PaymentPeriodDTO getPeriod() {
        return period;
    }

    public void setPeriod(PaymentPeriodDTO period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return "InstallmentPlansCreatedInsrcEvent{" +
                "paymentsTotalNumber=" + paymentsTotalNumber +
                ", paymentAmount=" + paymentAmount +
                ", period=" + period +
                '}';
    }
}
