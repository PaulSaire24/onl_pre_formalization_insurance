package com.bbva.rbvd.lib.r415.impl.pattern;

import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;
import com.bbva.rbvd.dto.insrncsale.policy.PolicyDTO;
import com.bbva.rbvd.lib.r047.RBVDR047;

public interface PreFormalizationInsurance {

    PolicyDTO start(PolicyDTO input, RBVDR047 rbvdr047, ApplicationConfigurationService applicationConfigurationService);

}
