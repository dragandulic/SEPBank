package com.example.Bank.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

//Kartica ustvari predstavlja kupca
@Entity
public class Card {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String pan;
	
	private String securitycode;
	
	private String cardholdername;
	
	@Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING,timezone = "Europe/Madrid")
	private Date expirationdate;
	
	@OneToOne
	private BankAccount bankaccount;
	
	
	public Card() {
		
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getPan() {
		return pan;
	}



	public void setPan(String pan) {
		this.pan = pan;
	}



	public String getSecuritycode() {
		return securitycode;
	}



	public void setSecuritycode(String securitycode) {
		this.securitycode = securitycode;
	}



	public String getCardholdername() {
		return cardholdername;
	}



	public void setCardholdername(String cardholdername) {
		this.cardholdername = cardholdername;
	}



	public Date getExpirationdate() {
		return expirationdate;
	}



	public void setExpirationdate(Date expirationdate) {
		this.expirationdate = expirationdate;
	}



	public BankAccount getBankaccount() {
		return bankaccount;
	}



	public void setBankaccount(BankAccount bankaccount) {
		this.bankaccount = bankaccount;
	}
	
	
}
