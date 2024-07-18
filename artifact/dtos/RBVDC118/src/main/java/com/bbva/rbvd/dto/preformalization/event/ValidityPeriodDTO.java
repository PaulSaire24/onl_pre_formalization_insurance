package com.bbva.rbvd.dto.preformalization.event;

import com.bbva.apx.dto.AbstractDTO;

import java.util.Date;

public class ValidityPeriodDTO extends AbstractDTO {
    private Date startDate;
    private Date endDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
