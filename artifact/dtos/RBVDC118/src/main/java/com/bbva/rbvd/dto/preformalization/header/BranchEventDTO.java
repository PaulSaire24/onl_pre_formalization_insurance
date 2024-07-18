package com.bbva.rbvd.dto.preformalization.header;

import com.bbva.apx.dto.AbstractDTO;

public class BranchEventDTO extends AbstractDTO {
    private String branchId;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    @Override
    public String toString() {
        return "BranchEventDTO{" +
                "branchId='" + branchId + '\'' +
                '}';
    }
}
