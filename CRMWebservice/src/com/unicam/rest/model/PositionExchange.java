package com.unicam.rest.model;

@Deprecated
public class PositionExchange {
	
	
	
	private double amount;
	private double unit_price;
	private String text;
	private double discount_in_percentage;
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
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
	public double getDiscount_in_percentage() {
		return discount_in_percentage;
	}
	public void setDiscount_in_percentage(double discount_in_percentage) {
		this.discount_in_percentage = discount_in_percentage;
	}
	
	
	
	
	
}
