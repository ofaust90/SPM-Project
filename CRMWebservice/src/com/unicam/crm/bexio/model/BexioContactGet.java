package com.unicam.crm.bexio.model;

/**
 * Since in POST request (e.g. like adding a new contact) bexio does not allow the object to have an id already.
 *  However, in a GET request (e.g. getting an specific customer) we want the id to be present.
 * @author oliverfaust
 *
 */
public class BexioContactGet extends BexioContact {

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
