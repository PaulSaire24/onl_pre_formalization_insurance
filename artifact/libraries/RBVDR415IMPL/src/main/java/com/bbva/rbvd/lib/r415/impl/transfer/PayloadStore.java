package com.bbva.rbvd.lib.r415.impl.transfer;

import com.bbva.rbvd.dto.cicsconnection.icr3.ICR3Response;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.preformalization.dao.QuotationDAO;

import java.math.BigDecimal;

public class PayloadStore {

    private PolicyDTO resposeBody;
    private QuotationDAO quotationDAO;
    private ICR3Response icr3Response;
    private BigDecimal paymentFrequencyId;
    private String paymentFrequencyName;

    public PolicyDTO getResposeBody() {
        return resposeBody;
    }

    public void setResposeBody(PolicyDTO resposeBody) {
        this.resposeBody = resposeBody;
    }

    public QuotationDAO getQuotationDAO() {
        return quotationDAO;
    }

    public void setQuotationDAO(QuotationDAO quotationDAO) {
        this.quotationDAO = quotationDAO;
    }

    public ICR3Response getIcr3Response() {
        return icr3Response;
    }

    public void setIcr3Response(ICR3Response icr3Response) {
        this.icr3Response = icr3Response;
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
        return "PayloadStore{" +
                "resposeBody=" + resposeBody +
                ", quotationDAO=" + quotationDAO +
                ", icr3Response=" + icr3Response +
                ", paymentFrequencyId=" + paymentFrequencyId +
                ", paymentFrequencyName='" + paymentFrequencyName + '\'' +
                '}';
    }
}
