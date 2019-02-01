package com.example.BankB.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.BankB.dto.CardDTO;
import com.example.BankB.dto.PCCrequestDTO;
import com.example.BankB.model.BankAccount;
import com.example.BankB.model.Card;
import com.example.BankB.model.Merchant;
import com.example.BankB.model.PCCrequest;
import com.example.BankB.model.PCCresponse;
import com.example.BankB.model.Request;
import com.example.BankB.repository.BankAccountRepository;
import com.example.BankB.repository.BankRepository;
import com.example.BankB.repository.CardRepository;
import com.example.BankB.repository.PCCrequestRepository;
import com.example.BankB.repository.PCCresponseRepository;
import com.example.BankB.repository.RequestRepository;



@Service
public class CardService {
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Autowired
	private PCCrequestRepository pccrequestRepository;
	
	@Autowired
	private PCCresponseRepository pccresponseRepository;
	
	
	public String checkcard(CardDTO card) {
		
		String numberOfBank="";
		
		String failedurl="";
		
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
						
						Request r = requestRepository.findByIdEquals(Long.parseLong(card.getRequestid()));
						
						failedurl = r.getFailedurl();
						
						Merchant m = bankRepository.findByMerchantIdEquals(r.getMerchant_id());
						
						//racun od prodavca
						BankAccount b1 = m.getBankaccount();
						double suma1 = b1.getSum();
						
						//racun od kupca
						BankAccount b2=cards.get(i).getBankaccount();
						double suma2=b2.getSum();
						
						
						if(suma2>=r.getAmount()) {
							suma1 = suma1 + r.getAmount();
							suma2 = suma2 - r.getAmount();
							b1.setSum(suma1);
							b2.setSum(suma2);
							bankAccountRepository.save(b1);
							bankAccountRepository.save(b2);
							
							//saljem koncetratoru placanja da je izvrsena transakcija
							
							
							String result = restTemplate.getForObject("http://localhost:8051/objectpayment/successpayment/" + r.getMerchant_order_id(), String.class);
							
							
							return r.getSuccessurl();
						}
						else {
							return r.getFailedurl();
						}						
					}				
				}						
			}
			return "Podaci o kartici nisu ispravni!";
			
		}
		else {
			//IDE NA PCC
			
			PCCrequest pccrequest = new PCCrequest();
			
			pccrequest.setPan(card.getPan());
			pccrequest.setSecuritycode(card.getSecuritycode());
			pccrequest.setCardholdername(card.getCardholdername());
			pccrequest.setExpirationdate(card.getExpirationdate());
			
			long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
			pccrequest.setAcquirer_order_id(number);
			
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			Date now=null;
			try {
				now = formatter.parse(timeStamp);
				} catch (ParseException e) {
				  e.printStackTrace();
				}
			
			pccrequest.setAcquirer_timestamp(now);
			
			Request r = requestRepository.findByIdEquals(Long.parseLong(card.getRequestid()));
			
			pccrequest.setAmount(r.getAmount());
			
			pccrequestRepository.save(pccrequest);
			
			HttpHeaders header = new HttpHeaders();
			HttpEntity entity = new HttpEntity(pccrequest, header);
			
			
			PCCresponse response = restTemplate.postForObject("http://localhost:8009/request/checkRequest", entity, PCCresponse.class);
			System.out.println(response);
			
		}
				
		return null;
	}
	
	//provera se kartica koja je stigla od banke A preko PCC
	public PCCresponse checkPCCrequest(PCCrequest card) {
		
		PCCresponse pccresponse = new PCCresponse();
		
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
					
					pccresponse.setCardauthentication(true);
					
					
					BankAccount b2=cards.get(i).getBankaccount();
					double suma2=b2.getSum();
					
					
					if(suma2>=card.getAmount()) {
						suma2 = suma2 - card.getAmount();
					
						b2.setSum(suma2);
						bankAccountRepository.save(b2);
						
						pccresponse.setTransactionauthorization(true);
						pccresponse.setAcquirer_order_id(card.getAcquirer_order_id());
						pccresponse.setAcquirer_timestamp(card.getAcquirer_timestamp());
						long number1 = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
						pccresponse.setIssuer_order_id(number1);
						pccresponse.setIssuer_timestamp(now);
						
						pccresponseRepository.save(pccresponse);
						
						return pccresponse;
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
		
		
		
		return pccresponse;
	}

}
