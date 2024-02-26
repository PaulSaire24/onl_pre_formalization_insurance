package com.bbva.rbvd.lib.r118.impl;

import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;
import com.bbva.elara.library.AbstractLibrary;
import com.bbva.elara.utility.interbackend.cics.InterBackendCicsUtils;
import com.bbva.pisd.lib.r012.PISDR012;
import com.bbva.pisd.lib.r401.PISDR401;
import com.bbva.rbvd.lib.r047.RBVDR047;
import com.bbva.rbvd.lib.r118.RBVDR118;

/**
 * This class automatically defines the libraries and utilities that it will use.
 */
public abstract class RBVDR118Abstract extends AbstractLibrary implements RBVDR118 {

    protected ApplicationConfigurationService applicationConfigurationService;

    protected InterBackendCicsUtils interBackendCicsUtils;

    protected PISDR401 pisdR401;

    protected PISDR012 pisdR012;

    protected RBVDR047 rbvdR047;

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

}