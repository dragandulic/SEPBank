package com.example.Bank.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;

import com.example.Bank.dto.CardDTO;
import com.example.Bank.dto.PCCrequestDTO;
import com.example.Bank.model.BankAccount;
import com.example.Bank.model.Card;
import com.example.Bank.model.Merchant;
import com.example.Bank.model.PCCrequest;
import com.example.Bank.model.Request;
import com.example.Bank.repository.BankAccountRepository;
import com.example.Bank.repository.BankRepository;
import com.example.Bank.repository.CardRepository;
import com.example.Bank.repository.PCCrequestRepository;
import com.example.Bank.repository.RequestRepository;

@Service
public class CardService {


	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private PCCrequestRepository pccrequestRepository;
	
	
	public String checkcard(CardDTO card) {
		
		
		String numberOfBank = "";
		String faildurl = "";
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
						
						Request r = requestRepository.findByIdEquals(Long.valueOf(card.getRequestid()).longValue());
						
						if(r!=null) {
							faildurl = r.getFailedurl();
							Card c = cardRepository.findByPanEquals(card.getPan());
							BankAccount ba = c.getBankaccount();
							
							Merchant m = bankRepository.findByMerchantIdEquals(r.getMerchant_id());
							BankAccount mba = m.getBankaccount();
							
							if(r.getAmount()<=ba.getSum()) {
								
								ba.setSum(ba.getSum()-r.getAmount());
								bankAccountRepository.save(ba);
														
								mba.setSum(mba.getSum()+r.getAmount());
								bankAccountRepository.save(mba);
								
								String result = restTemplate.getForObject("http://localhost:8051/objectpayment/successpayment/" + r.getMerchant_order_id(), String.class);
								
								return r.getSuccessurl();
							}
							else {
								return faildurl;
							}
							
						}
					}
				}
			}
			return "podaci o kartici nisu ispravni";
		}
		else {
			//IDE NA PCC
			
			PCCrequest pccr = new PCCrequest();
			pccr.setPan(card.getPan());
			pccr.setCardholdername(card.getCardholdername());
			pccr.setExpirationdate(card.getExpirationdate());
			pccr.setSecuritycode(card.getSecuritycode());
			Request r = requestRepository.findByIdEquals(Long.valueOf(card.getRequestid()).longValue());
			pccr.setAmount(r.getAmount());
			long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
			pccr.setAcquirer_order_id(number);
			
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			Date now=null;
			try {
				now = formatter.parse(timeStamp);
				} catch (ParseException e) {
				  e.printStackTrace();
				}

			pccr.setAcquirer_timestamp(now);
			
			pccrequestRepository.save(pccr);
			
			HttpHeaders header = new HttpHeaders();	
			HttpEntity entity = new HttpEntity(pccr, header);
			
			String s = restTemplate.postForObject("http://localhost:8009/request/checkRequest", entity, String.class);
			System.out.println(s);
		}
		
		

		return null;
	}
	
	
	
	//provera se kartica koja je stigla od druge banke preko PCC
	public String checkPCCrequest(PCCrequest card) {
			
			
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
					
					Card c = cardRepository.findByPanEquals(card.getPan());
					BankAccount ba = c.getBankaccount();
					
					if(card.getAmount()<=ba.getSum()) {
						
						ba.setSum(ba.getSum()-card.getAmount());
						bankAccountRepository.save(ba);
						return "okk";
					}
					
						
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
