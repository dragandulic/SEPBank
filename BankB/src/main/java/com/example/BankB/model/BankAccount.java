package com.example.BankB.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BankAccount {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String numberofaccount;
	
	private double sum;
	
	public BankAccount() {
		
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumberofaccount() {
		return numberofaccount;
	}

	public void setNumberofaccount(String numberofaccount) {
		this.numberofaccount = numberofaccount;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

}
