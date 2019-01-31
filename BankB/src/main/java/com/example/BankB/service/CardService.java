package com.example.BankB.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.BankB.dto.CardDTO;
import com.example.BankB.dto.PCCrequestDTO;
import com.example.BankB.model.Card;
import com.example.BankB.repository.CardRepository;



@Service
public class CardService {
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	public String checkcard(CardDTO card) {
		
		String numberOfBank="";
		
		for(int i=0; i<3; i++) {
			numberOfBank += card.getPan().charAt(i);
		}
		
		if(numberOfBank.equals("411")) {
			
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
			//IDE NA PCC
			
			PCCrequestDTO pccrequest = new PCCrequestDTO();
			
			pccrequest.setPan(card.getPan());
			pccrequest.setSecuritycode(card.getSecuritycode());
			pccrequest.setCardholdername(card.getCardholdername());
			pccrequest.setExpirationdate(card.getExpirationdate());
			
			HttpHeaders header = new HttpHeaders();
			HttpEntity entity = new HttpEntity(pccrequest, header);
			
			
			String response = restTemplate.postForObject("http://localhost:8009/request/checkRequest", entity, String.class);
			System.out.println(response);
			
		}
		
		
		
		return null;
	}
	
	//provera se kartica koja je stigla od banke A preko PCC
	public String checkPCCrequest(PCCrequestDTO card) {
		
		
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
					System.out.println("PRODAVAC I KUPAC NISU U ISTOJ BANCI ");
					System.out.println("KARTICA KUPCA JE VALIDNA");
					
				}
				else {
					System.out.println("KARTICA KUPCA NIJE VALIDNA ZBOG DATUMA");
				}
			
			}
			else {
				System.out.println("KARTICA KUPCA NIJE VALIDNA ZBOG OSTALIH PODATAKA");
			}
			
		}
		
		
		
		return null;
	}

}
