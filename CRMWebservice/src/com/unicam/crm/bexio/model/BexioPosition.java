package com.unicam.crm.bexio.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BexioPosition {
	

	private int amount;
	private double unit_price;
	private String text;
	private int account_id;
	private int tax_id;
	private double discount_in_percent;
	
	private String type ;
	
	public BexioPosition() {
		this.account_id = 159;
		this.tax_id = 3;
		this.type = "KbPositionCustom";
	}
	
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public int getTax_id() {
		return tax_id;
	}
	public void setTax_id(int tax_id) {
		this.tax_id = tax_id;
	}
	public double getDiscount_in_percent() {
		return discount_in_percent;
	}
	public void setDiscount_in_percent(double discount_in_percentage) {
		this.discount_in_percent = discount_in_percentage;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
	/*
	account_id	required	Resource account	
	amount	required	decimal	
	tax_id	required	Resource tax	
	unit_price	required	decimal	
	discount_in_percent	optional	decimal	
	text	optional	string (4000)
	
	*/


}
