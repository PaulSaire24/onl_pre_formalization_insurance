package com.bbva.rbvd.lib.r415.impl.transfer;

import com.bbva.rbvd.dto.cicsconnection.icr2.ICR2Response;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.preformalization.dao.QuotationDAO;

import java.math.BigDecimal;

public class PayloadStore {

    private PolicyDTO resposeBody;
    private QuotationDAO quotationDAO;
    private boolean isEndorsement;
    private ICR2Response icr2Response;
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

    public boolean getIsEndorsement() {
        return isEndorsement;
    }

    public void setEndorsement(boolean endorsement) {
        isEndorsement = endorsement;
    }

    public ICR2Response getIcr2Response() {
        return icr2Response;
    }

    public void setIcr2Response(ICR2Response icr2Response) {
        this.icr2Response = icr2Response;
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
}
