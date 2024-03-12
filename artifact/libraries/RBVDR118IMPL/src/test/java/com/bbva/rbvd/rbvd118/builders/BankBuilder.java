package com.bbva.rbvd.rbvd118.builders;

import com.bbva.rbvd.dto.insrncsale.commons.BankDTO;
import com.bbva.rbvd.dto.insrncsale.commons.BranchDTO;

public class BankBuilder {

    private final BankDTO bank;

    public BankBuilder() {
        bank = new BankDTO();
    }

    public static BankBuilder instance() {
        return new BankBuilder();
    }

    public BankBuilder withId(String id) {
        bank.setId(id);
        return this;
    }

    public BankBuilder withBranch(String branchId) {
        BranchDTO branch = new BranchDTO();
        branch.setId(branchId);
        bank.setBranch(branch);
        return this;
    }

    public BankDTO build() {
        return bank;
    }
}
