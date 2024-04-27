package com.bbva.rbvd.lib.rbvd118.impl;

import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;
import com.bbva.elara.library.AbstractLibrary;
import com.bbva.elara.utility.api.connector.APIConnector;
import com.bbva.elara.utility.api.connector.APIConnectorBuilder;
import com.bbva.elara.utility.interbackend.cics.InterBackendCicsUtils;
import com.bbva.pisd.lib.r012.PISDR012;
import com.bbva.pisd.lib.r226.PISDR226;
import com.bbva.pisd.lib.r401.PISDR401;
import com.bbva.rbvd.lib.rbvd118.impl.util.ValidationUtil;
import com.bbva.rbvd.lib.r047.RBVDR047;
import com.bbva.rbvd.lib.rbvd118.RBVDR118;
import com.bbva.rbvd.lib.rbvd118.impl.business.ICR2Business;
import com.bbva.rbvd.lib.rbvd118.impl.util.MapperHelper;

/**
 * This class automatically defines the libraries and utilities that it will use.
 */
public abstract class RBVDR118Abstract extends AbstractLibrary implements RBVDR118 {

    protected ApplicationConfigurationService applicationConfigurationService;

    protected InterBackendCicsUtils interBackendCicsUtils;

    protected APIConnector internalApiConnector;

    protected APIConnectorBuilder apiConnectorBuilder;

    protected PISDR401 pisdR401;

    protected PISDR012 pisdR012;

    protected RBVDR047 rbvdR047;

    protected ValidationUtil validationUtil;

    protected MapperHelper mapperHelper;

    protected ICR2Business icr2Business;
    protected PISDR226 pisdr226;


    /**
     * @param applicationConfigurationService the this.applicationConfigurationService to set
     */
    public void setApplicationConfigurationService(ApplicationConfigurationService applicationConfigurationService) {
        this.applicationConfigurationService = applicationConfigurationService;
    }

    /**
     * @param interBackendCicsUtils the this.interBackendCicsUtils to set
     */
    public void setInterBackendCicsUtils(InterBackendCicsUtils interBackendCicsUtils) {
        this.interBackendCicsUtils = interBackendCicsUtils;
    }

    /**
     * @param internalApiConnector the this.internalApiConnector to set
     */
    public void setInternalApiConnector(APIConnector internalApiConnector) {
        this.internalApiConnector = internalApiConnector;
    }

    /**
     * @param apiConnectorBuilder the this.apiConnectorBuilder to set
     */
    public void setApiConnectorBuilder(APIConnectorBuilder apiConnectorBuilder) {
        this.apiConnectorBuilder = apiConnectorBuilder;
    }

    /**
     * @param pisdR401 the this.pisdR401 to set
     */
    public void setPisdR401(PISDR401 pisdR401) {
        this.pisdR401 = pisdR401;
    }

    /**
     * @param pisdR012 the this.pisdR012 to set
     */
    public void setPisdR012(PISDR012 pisdR012) {
        this.pisdR012 = pisdR012;
    }

    /**
     * @param rbvdR047 the this.rbvdR047 to set
     */
    public void setRbvdR047(RBVDR047 rbvdR047) {
        this.rbvdR047 = rbvdR047;
    }

    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    public void setMapperHelper(MapperHelper mapperHelper) {
        this.mapperHelper = mapperHelper;
    }

    public void setIcr2Business(ICR2Business icr2Business) {
        this.icr2Business = icr2Business;
    }

    public void setPisdr226(PISDR226 pisdr226) {
        this.pisdr226 = pisdr226;
    }
}