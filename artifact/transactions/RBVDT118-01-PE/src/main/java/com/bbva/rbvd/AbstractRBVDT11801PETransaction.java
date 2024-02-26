package com.bbva.rbvd;

import com.bbva.elara.transaction.AbstractTransaction;

/**
 * In this class, the input and output data is defined automatically through the setters and getters.
 */
public abstract class AbstractRBVDT11801PETransaction extends AbstractTransaction {

	public AbstractRBVDT11801PETransaction(){
	}


	/**
	 * Return value for input parameter alias
	 */
	protected String getAlias(){
		return (String)this.getParameter("alias");
	}
}
