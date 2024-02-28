package com.bbva.rbvd.dto.preformalization;

public class RenewalPolicy {
    private Long counter;
    private String renewalDate;
    private Reason reason;

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public String getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(String renewalDate) {
        this.renewalDate = renewalDate;
    }

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "RenewalPolicy{" +
                "counter=" + counter +
                ", renewalDate='" + renewalDate + '\'' +
                ", reason=" + reason.toString() +
                '}';
    }
}
