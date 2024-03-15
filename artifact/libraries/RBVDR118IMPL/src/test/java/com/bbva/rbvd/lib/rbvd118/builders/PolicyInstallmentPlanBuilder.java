package com.bbva.rbvd.lib.rbvd118.builders;

import com.bbva.rbvd.dto.insrncsale.commons.PaymentAmountDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PaymentPeriodDTO;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyInstallmentPlanDTO;

public class PolicyInstallmentPlanBuilder {
    private final PolicyInstallmentPlanDTO policyInstallmentPlanDTO;

    private PolicyInstallmentPlanBuilder() {
        policyInstallmentPlanDTO = new PolicyInstallmentPlanDTO();
    }

    public static PolicyInstallmentPlanBuilder instance() {
        return new PolicyInstallmentPlanBuilder();
    }

    public PolicyInstallmentPlanBuilder withTotalNumberOfInstallments(Long totalNumberOfInstallments) {
        policyInstallmentPlanDTO.setTotalNumberInstallments(totalNumberOfInstallments);
        return this;
    }

    public PolicyInstallmentPlanBuilder withPaymentAmount(PaymentAmountDTO paymentAmount) {
        policyInstallmentPlanDTO.setPaymentAmount(paymentAmount);
        return this;
    }

    public PolicyInstallmentPlanBuilder withPeriod(String periodId) {
        PaymentPeriodDTO periodDTO = new PaymentPeriodDTO();
        periodDTO.setId(periodId);
        policyInstallmentPlanDTO.setPeriod(periodDTO);
        return this;
    }

    public PolicyInstallmentPlanDTO build() {
        return policyInstallmentPlanDTO;
    }
}
