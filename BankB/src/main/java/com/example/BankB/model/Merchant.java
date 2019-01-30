package com.example.BankB.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;



@Entity
public class Merchant {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String merchant_id;
	
	private String merchant_password;
	
	@OneToOne
	private BankAccount bankaccount;
	
	public Merchant() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public BankAccount getBankaccount() {
		return bankaccount;
	}

	public void setBankaccount(BankAccount bankaccount) {
		this.bankaccount = bankaccount;
	}
}
