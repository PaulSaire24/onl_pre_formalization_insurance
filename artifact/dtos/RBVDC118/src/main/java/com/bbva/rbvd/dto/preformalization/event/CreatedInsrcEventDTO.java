package com.bbva.rbvd.dto.preformalization.event;

import com.bbva.apx.dto.AbstractDTO;
import com.bbva.rbvd.dto.preformalization.header.HeaderDTO;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonIgnoreProperties(value = {"type"})
public class CreatedInsrcEventDTO extends AbstractDTO {
    @JsonIgnore
    private InsurancePreFormalizedDTO insurancePreFormalized;
    private HeaderDTO header;


    public InsurancePreFormalizedDTO getInsurancePreFormalized() {
        return insurancePreFormalized;
    }

    public void setInsurancePreFormalized(InsurancePreFormalizedDTO insurancePreFormalized) {
        this.insurancePreFormalized = insurancePreFormalized;
    }

    public HeaderDTO getHeader() {
        return header;
    }

    public void setHeader(HeaderDTO header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "CreatedInsrcEventDTO{" +
                "insurancePreFormalized=" + insurancePreFormalized +
                ", header=" + header +
                '}';
    }
}
