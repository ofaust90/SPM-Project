package com.unicam.crm.bexio.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.unicam.rest.model.Offer;
import com.unicam.rest.model.PositionExchange;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BexioOffer implements Offer {
	
	
	
	private int contact_id;
	private int user_id = 1;
	 
	private List<BexioPosition> positions;
	//private String positions;
	
	
	public int getContact_id() {
		return contact_id;
	}
	public void setContact_id(int contact_id) {
		this.contact_id = contact_id;
	}
	
	
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	/*
	public String getPositions() {
		return positions;
	}
	public void setPositions(String positions) {
		this.positions = positions;
	}
	*/
	public List<BexioPosition> getPositions() {
		return positions;
	}
	public void setPositions(List<BexioPosition> positions) {
		this.positions = positions;
	}
	

}
