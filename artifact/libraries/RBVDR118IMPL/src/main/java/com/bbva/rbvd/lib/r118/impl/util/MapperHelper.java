package com.bbva.rbvd.lib.r118.impl.util;

import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;
import com.bbva.rbvd.dto.insrncsale.utils.RBVDProperties;

import java.util.HashMap;
import java.util.Map;

public class MapperHelper {
    ApplicationConfigurationService applicationConfigurationService;

    public Map<String, Object> createSingleArgument(String argument, String parameterName) {
        Map<String, Object> mapArgument = new HashMap<>();
        if (RBVDProperties.FIELD_POLICY_PAYMENT_FREQUENCY_TYPE.getValue().equals(parameterName)) {
            String frequencyType = applicationConfigurationService.getProperty(argument);
            mapArgument.put(parameterName, frequencyType);
            return mapArgument;
        }
        mapArgument.put(parameterName, argument);
        return mapArgument;
    }
}
