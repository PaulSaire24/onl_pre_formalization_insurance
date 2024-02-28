package com.bbva.rbvd.dto.preformalization.input;

import com.bbva.rbvd.dto.insrncsale.commons.CommonFieldsDTO;
import com.bbva.rbvd.dto.insrncsale.commons.PaymentAmountDTO;

public class Installment extends CommonFieldsDTO {
    private PaymentAmountDTO installmentAmount;
    private ValidityPeriod period;
    private InstallmentType installmentType;
    private String externalDocumentationSendDate;

    public PaymentAmountDTO getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(PaymentAmountDTO installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public ValidityPeriod getPeriod() {
        return period;
    }

    public void setPeriod(ValidityPeriod period) {
        this.period = period;
    }

    public InstallmentType getInstallmentType() {
        return installmentType;
    }

    public void setInstallmentType(InstallmentType installmentType) {
        this.installmentType = installmentType;
    }

    public String getExternalDocumentationSendDate() {
        return externalDocumentationSendDate;
    }

    public void setExternalDocumentationSendDate(String externalDocumentationSendDate) {
        this.externalDocumentationSendDate = externalDocumentationSendDate;
    }

    @Override
    public String toString() {
        return "Installment{" +
                "installmentAmount=" + installmentAmount.toString() +
                ", period=" + period.toString() +
                ", installmentType=" + installmentType.toString() +
                ", externalDocumentationSendDate='" + externalDocumentationSendDate + '\'' +
                '}';
    }
}
