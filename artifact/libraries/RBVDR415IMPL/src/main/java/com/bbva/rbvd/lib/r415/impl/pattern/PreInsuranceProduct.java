package com.bbva.rbvd.lib.r415.impl.pattern;

import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.dto.preformalization.transfer.PayloadConfig;

public interface PreInsuranceProduct {
    PayloadConfig getConfig(PolicyDTO input);
}
