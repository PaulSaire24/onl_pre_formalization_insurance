package com.bbva.rbvd.dto.preformalization.header;

import com.bbva.apx.dto.AbstractDTO;

public class ResultDTO extends AbstractDTO {
    private String returnCode;
    private String returnDefinition;

    public ResultDTO(String returnCode, String returnDefinition) {
        this.returnCode = returnCode;
        this.returnDefinition = returnDefinition;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnDefinition() {
        return returnDefinition;
    }

    public void setReturnDefinition(String returnDefinition) {
        this.returnDefinition = returnDefinition;
    }

    @Override
    public String toString() {
        return "ResultDTO{" +
                "returnCode='" + returnCode + '\'' +
                ", returnDefinition='" + returnDefinition + '\'' +
                '}';
    }
}
