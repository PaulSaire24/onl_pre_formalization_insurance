package com.bbva.rbvd.dto.preformalization.event;

import com.bbva.apx.dto.AbstractDTO;

public class TotalInstallmentDTO extends AbstractDTO {
    private Double amount;
    private String currency;
    private PaymentPeriodDTO period;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public PaymentPeriodDTO getPeriod() {
        return period;
    }

    public void setPeriod(PaymentPeriodDTO period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return "TotalInstallmentDTO{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                ", period=" + period +
                '}';
    }
}
