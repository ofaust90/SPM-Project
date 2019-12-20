package com.unicam.crm.bexio.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.unicam.rest.model.Customer;
/**
 * Will be mapped to CustomerExchange Model (Equivalent to Customer)
 * @author oliverfaust
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class BexioContact implements Customer {
	

	private String name_1;
	private String name_2;
	private String user_id;
	private String owner_id;
	private String contact_type_id;
	private String address;
	private String city;
	private String remarks; //serves as customer type identifier
	private String mail;
	private String postcode;
	
	
	
	
	public BexioContact() {
		this.user_id = "1";
		this.owner_id = "1";
		this.contact_type_id = "1";
		
	}
	
	public String getName_1() {
		return name_1;
	}
	public void setName_1(String name_1) {
		this.name_1 = name_1;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}
	public String getContact_type_id() {
		return contact_type_id;
	}
	public void setContact_type_id(String contact_type_id) {
		this.contact_type_id = contact_type_id;
	}

	public String getName_2() {
		return name_2;
	}

	public void setName_2(String name_2) {
		this.name_2 = name_2;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	
	

}
