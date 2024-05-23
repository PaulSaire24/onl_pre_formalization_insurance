package com.bbva.rbvd.lib.r415.impl;

import com.bbva.elara.configuration.manager.application.ApplicationConfigurationService;
import com.bbva.elara.library.AbstractLibrary;
import com.bbva.elara.utility.api.connector.APIConnector;
import com.bbva.elara.utility.api.connector.APIConnectorBuilder;
import com.bbva.pisd.lib.r012.PISDR012;
import com.bbva.pisd.lib.r226.PISDR226;
import com.bbva.pisd.lib.r401.PISDR401;
import com.bbva.pisd.lib.r601.PISDR601;
import com.bbva.rbvd.lib.r047.RBVDR047;
import com.bbva.rbvd.lib.r415.RBVDR415;

/**
 * This class automatically defines the libraries and utilities that it will use.
 */
public abstract class RBVDR415Abstract extends AbstractLibrary implements RBVDR415 {

	protected ApplicationConfigurationService applicationConfigurationService;

	protected APIConnector internalApiConnector;

	protected APIConnectorBuilder apiConnectorBuilder;
	protected APIConnector internalApiConnectorImpersonation;

	protected PISDR401 pisdR401;

	protected PISDR012 pisdR012;

	protected RBVDR047 rbvdR047;

	protected PISDR226 pisdR226;

	protected PISDR601 pisdR601;


	/**
	* @param applicationConfigurationService the this.applicationConfigurationService to set
	*/
	public void setApplicationConfigurationService(ApplicationConfigurationService applicationConfigurationService) {
		this.applicationConfigurationService = applicationConfigurationService;
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

	/**
	* @param pisdR226 the this.pisdR226 to set
	*/
	public void setPisdR226(PISDR226 pisdR226) {
		this.pisdR226 = pisdR226;
	}

	/**
	* @param pisdR601 the this.pisdR601 to set
	*/
	public void setPisdR601(PISDR601 pisdR601) {
		this.pisdR601 = pisdR601;
	}

	public void setInternalApiConnectorImpersonation(APIConnector internalApiConnectorImpersonation) {
		this.internalApiConnectorImpersonation = internalApiConnectorImpersonation;
	}
}