package com.example.BankB.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Request {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String merchant_id;
	
	private String merchant_password;
	
	private double amount;
	
	private String merchant_order_id;
	
	public Request() {
		
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

	public String getMerchant_order_id() {
		return merchant_order_id;
	}

	public void setMerchant_order_id(String merchant_order_id) {
		this.merchant_order_id = merchant_order_id;
	}

}
