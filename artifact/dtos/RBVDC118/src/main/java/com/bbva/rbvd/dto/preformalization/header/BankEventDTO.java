package com.bbva.rbvd.dto.preformalization.header;

import com.bbva.apx.dto.AbstractDTO;

public class BankEventDTO extends AbstractDTO {

    private String bankId;
    private BranchEventDTO branch;
    public BankEventDTO(String bankId, BranchEventDTO branch) {
        this.bankId = bankId;
        this.branch = branch;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public BranchEventDTO getBranch() {
        return branch;
    }

    public void setBranch(BranchEventDTO branch) {
        this.branch = branch;
    }

    @Override
    public String toString() {
        return "BankEventDTO{" +
                "bankId='" + bankId + '\'' +
                ", branch=" + branch +
                '}';
    }
}
