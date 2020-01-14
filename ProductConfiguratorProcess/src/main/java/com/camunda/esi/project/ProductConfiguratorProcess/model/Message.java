package com.camunda.esi.project.ProductConfiguratorProcess.model;

/**
 * Camunda Message POJO Object
 * @author oliverfaust
 *
 */
public class Message {
	
	private String messageName;
	private String businessKey;
	
	private boolean all;
	
	public String getMessageName() {
		return messageName;
	}
	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	
	public boolean isAll() {
		return all;
	}
	public void setAll(boolean all) {
		this.all = all;
	}

	
	
}
