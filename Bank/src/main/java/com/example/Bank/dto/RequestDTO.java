package com.example.Bank.dto;

public class RequestDTO {

	
	private Long id;

	private String merchant_id;
	
	private String merchant_password;
	
	private double amount;
	
	private double amountMag;
	
	private String merchant_order_id;
	
	private RequestDTO() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMerchant_id() {
		return merchant_id;
	}

	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}

	public String getMerchant_password() {
		return merchant_password;
	}

	public void setMerchant_password(String merchant_password) {
		this.merchant_password = merchant_password;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAmountMag() {
		return amountMag;
	}

	public void setAmountMag(double amountMag) {
		this.amountMag = amountMag;
	}

	public String getMerchant_order_id() {
		return merchant_order_id;
	}

	public void setMerchant_order_id(String merchant_order_id) {
		this.merchant_order_id = merchant_order_id;
	}
	
}
