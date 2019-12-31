package com.unicam.rest.model;

import java.util.List;

public class OfferExchange implements Offer {

	
	private int id;
	private int contact_id;
	private String positions;
	private double price;
	private double discount;
	private int amount;
	private double total_price;
	//private String positions;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getContact_id() {
		return contact_id;
	}
	public void setContact_id(int contact_id) {
		this.contact_id = contact_id;
	}
	public String getPositions() {
		return positions;
	}
	public void setPositions(String positions) {
		this.positions = positions;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getTotal_price() {
		return total_price;
	}
	public void setTotal_price(double total_price) {
		this.total_price = total_price;
	}
	
	
	/*
	public String getPositions() {
		return positions;
	}
	public void setPositions(String positions) {
		this.positions = positions;
	}
	
	public List<PositionExchange> getPositions() {
		return positions;
	}
	public void setPositions(List<PositionExchange> positions) {
		this.positions = positions;
	}
	*/
	
}
