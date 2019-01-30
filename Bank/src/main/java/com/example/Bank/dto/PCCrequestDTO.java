package com.example.Bank.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PCCrequestDTO {

	
	private String pan;
	
	private String securitycode;
	
	private String cardholdername;
	
	@Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING,timezone = "Europe/Madrid")
	private Date expirationdate;
	
	private String acquirer_order_id;
	
	private String acquirer_timestamp;
	
	public PCCrequestDTO() {
		
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

	public String getAcquirer_order_id() {
		return acquirer_order_id;
	}

	public void setAcquirer_order_id(String acquirer_order_id) {
		this.acquirer_order_id = acquirer_order_id;
	}

	public String getAcquirer_timestamp() {
		return acquirer_timestamp;
	}

	public void setAcquirer_timestamp(String acquirer_timestamp) {
		this.acquirer_timestamp = acquirer_timestamp;
	}
	
	
}
