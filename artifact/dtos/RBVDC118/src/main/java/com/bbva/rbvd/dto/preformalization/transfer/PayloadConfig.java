package com.bbva.rbvd.dto.preformalization.transfer;

import com.bbva.rbvd.dto.preformalization.dao.QuotationDAO;

import java.math.BigDecimal;

public class PayloadConfig {

    private QuotationDAO quotation;
    private BigDecimal paymentFrequencyId;
    private String paymentFrequencyName;

    public QuotationDAO getQuotation() {
        return quotation;
    }

    public void setQuotation(QuotationDAO quotation) {
        this.quotation = quotation;
    }

    public BigDecimal getPaymentFrequencyId() {
        return paymentFrequencyId;
    }

    public void setPaymentFrequencyId(BigDecimal paymentFrequencyId) {
        this.paymentFrequencyId = paymentFrequencyId;
    }

    public String getPaymentFrequencyName() {
        return paymentFrequencyName;
    }

    public void setPaymentFrequencyName(String paymentFrequencyName) {
        this.paymentFrequencyName = paymentFrequencyName;
    }

    @Override
    public String toString() {
        return "PayloadConfig{" +
                "quotation=" + quotation +
                ", paymentFrequencyId=" + paymentFrequencyId +
                ", paymentFrequencyName='" + paymentFrequencyName + '\'' +
                '}';
    }
}
