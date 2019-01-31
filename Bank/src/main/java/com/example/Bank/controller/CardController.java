package com.example.Bank.controller;


import java.text.DateFormat;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Bank.dto.CardDTO;
import com.example.Bank.model.Card;
import com.example.Bank.service.CardService;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

@RestController
@RequestMapping("/card")
public class CardController {
	
	
	@Autowired
	private CardService cardService;
	
	
	public static final Pattern VALID_PAN_REGEX = 
		    Pattern.compile("^[0-9]{4} [0-9]{4} [0-9]{4} [0-9]{4}$", Pattern.CASE_INSENSITIVE);
	
	public static final Pattern VALID_SECURITYCODE_REGEX = 
		    Pattern.compile("^[0-9]{3}$", Pattern.CASE_INSENSITIVE);
	
	public static final Pattern VALID_DATE_REGEX = 
		    Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}$", Pattern.CASE_INSENSITIVE);
	
	
	
	
	
	
	@PostMapping("/checkCard")
	public void check(@RequestBody CardDTO card) {
		boolean valid = false;
		
		Matcher matcher = VALID_PAN_REGEX .matcher(card.getPan());
		Matcher matcher1 = VALID_SECURITYCODE_REGEX .matcher(card.getSecuritycode());
		 
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String reportDate = df.format(card.getExpirationdate());
		
		Matcher matcher2 = VALID_DATE_REGEX .matcher(reportDate);
		 
		if(matcher.find() && matcher1.find() && matcher2.find()){
			String res = cardService.checkcard(card);
		}else{
			System.out.println("NEVALIDNOOOOOOOOOOOOOOOOO");
		}
		
	
		
	}

}
