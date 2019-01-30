package com.example.Bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;

import com.example.Bank.dto.CardDTO;
import com.example.Bank.model.Card;
import com.example.Bank.repository.CardRepository;

@Service
public class CardService {


	@Autowired
	private CardRepository cardRepository;
	
	
	public String checkcard(CardDTO card) {
		
		
		String numberOfBank = "";
		
		for(int i=0;i<3;i++) {
			numberOfBank += card.getPan().charAt(i);
		}
		
		if(numberOfBank.equals("977")) {
			List<Card> cards = cardRepository.findAll();
			
			for(int i=0; i<cards.size(); i++) {
				if(cards.get(i).getPan().equals(card.getPan()) && cards.get(i).getSecuritycode().equals(card.getSecuritycode())
						&& cards.get(i).getCardholdername().equals(card.getCardholdername())) {
					
					
					String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					
					Date now=null;
					try {
						now = formatter.parse(timeStamp);
						} catch (ParseException e) {
						  e.printStackTrace();
						}
					
					if(now.compareTo(card.getExpirationdate())<=0){
						System.out.println("PRODAVAC I KUPAC U ISTOJ BANCI ");
						System.out.println("KARTICA KUPCA JE VALIDNA");
						
					}
				}
			}
			
		}
		else {
			
		}
		
		
		
		
		
		
		
		
		return null;
	}
	
}
