package com.example.Bank.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class PCCresponse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long acquirer_order_id;
	
	@Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", shape = JsonFormat.Shape.STRING,timezone = "Europe/Madrid")
	private Date acquirer_timestamp;
	
	private Long issuer_order_id;
	
	@Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", shape = JsonFormat.Shape.STRING,timezone = "Europe/Madrid")
	private Date issuer_timestamp;
	
	private boolean cardauthentication;
	
	private boolean transactionauthorization;

	public PCCresponse() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAcquirer_order_id() {
		return acquirer_order_id;
	}

	public void setAcquirer_order_id(Long acquirer_order_id) {
		this.acquirer_order_id = acquirer_order_id;
	}

	public Date getAcquirer_timestamp() {
		return acquirer_timestamp;
	}

	public void setAcquirer_timestamp(Date acquirer_timestamp) {
		this.acquirer_timestamp = acquirer_timestamp;
	}

	public Long getIssuer_order_id() {
		return issuer_order_id;
	}

	public void setIssuer_order_id(Long issuer_order_id) {
		this.issuer_order_id = issuer_order_id;
	}

	public Date getIssuer_timestamp() {
		return issuer_timestamp;
	}

	public void setIssuer_timestamp(Date issuer_timestamp) {
		this.issuer_timestamp = issuer_timestamp;
	}

	public boolean isCardauthentication() {
		return cardauthentication;
	}

	public void setCardauthentication(boolean cardauthentication) {
		this.cardauthentication = cardauthentication;
	}

	public boolean isTransactionauthorization() {
		return transactionauthorization;
	}

	public void setTransactionauthorization(boolean transactionauthorization) {
		this.transactionauthorization = transactionauthorization;
	}
	
	
	
}
