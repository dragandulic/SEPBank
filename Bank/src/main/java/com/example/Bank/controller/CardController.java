package com.example.Bank.controller;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Bank.model.Card;
import com.example.Bank.repository.CardRepository;

@RestController
@RequestMapping("/card")
public class CardController {
	
	@Autowired
	private CardRepository cardRepository;
	
	@PostMapping("/checkCard")
	public void check(@RequestBody Card card) {
		
		List<Card> cards = cardRepository.findAll();
		
		for(int i=0; i<cards.size(); i++) {
			if(cards.get(i).getPan().equals(card.getPan()) && cards.get(i).getSecuritycode().equals(card.getSecuritycode())
					&& cards.get(i).getCardholdername().equals(card.getCardholdername())) {
				
				
				String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
				System.out.println(timeStamp);
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				
				Date now=null;
				try {
					now = formatter.parse(timeStamp);
					} catch (ParseException e) {
					  e.printStackTrace();
					}
				
				if(now.compareTo(card.getExpirationdate())<=0){
					System.out.println("PODACI KUPCA: " + cards.get(i).getPan());
					
					
					
				}
				
			}
		}
		
		
		
	}

}
