package com.example.Bank.service;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.text.ParseException;

import com.example.Bank.controller.RequestController;
import com.example.Bank.dto.CardDTO;
import com.example.Bank.dto.PCCrequestDTO;
import com.example.Bank.model.BankAccount;
import com.example.Bank.model.Card;
import com.example.Bank.model.Merchant;
import com.example.Bank.model.PCCrequest;
import com.example.Bank.model.PCCresponse;
import com.example.Bank.model.Request;
import com.example.Bank.repository.BankAccountRepository;
import com.example.Bank.repository.BankRepository;
import com.example.Bank.repository.CardRepository;
import com.example.Bank.repository.PCCrequestRepository;
import com.example.Bank.repository.PCCresponseRepository;
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
	
	@Autowired
	private PCCresponseRepository pccresponseRepository;
	
	private Logger logger = Logger.getLogger(CardService.class);
	
	
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
					
					
					String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					
					Date now=null;
					try {
						now = formatter.parse(timeStamp);
						} catch (ParseException e) {
						  e.printStackTrace();
						}
					
					if(now.compareTo(card.getExpirationdate())<=0){						
						
						Request r = requestRepository.findByIdEquals(Long.valueOf(card.getRequestid()).longValue());
						logger.info("Method: checkcard -> Merchant '"+ r.getMerchant_id()+"' and customer, PAN is '"+card.getPan()+"', are from the same bank! ");
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
								
								
								Map<String, Object> mapa = new HashMap<>();
								mapa.put("status", "paid");
								mapa.put("type", "bank");
								mapa.put("currency", "EUR");
								mapa.put("amount", r.getAmount());
								mapa.put("merchant", m.getMerchant_id());
								
								String reportDate = formatter.format(now);
								mapa.put("time",reportDate );
								
								
								HttpHeaders h = new HttpHeaders();
								
								HttpEntity<Map<String, Object>> e = new HttpEntity<Map<String, Object>>(mapa, h);
								
								String re = restTemplate.postForObject("http://localhost:8051/objectpayment/successpayment/" + r.getMerchant_order_id(), e, String.class);
								
								
								
								//String result = restTemplate.getForObject("http://localhost:8051/objectpayment/successpayment/" + r.getMerchant_order_id(), String.class);
								
								return r.getSuccessurl();
							}
							else {
								logger.info("Method: checkcard -> Customer, PAN is '"+card.getPan()+"', doesn't have enough money!");
								return faildurl;
							}
							
						}
					}
				}
			}
			logger.info("Method: checkcard -> Card data, PAN is '"+card.getPan()+"', isn't valid!");
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
			logger.info("Method: checkcard -> Merchant '"+ r.getMerchant_id()+"' and customer, PAN is '"+card.getPan()+"', aren't from the same bank! ");
			long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
			pccr.setAcquirer_order_id(number);
			
			pccr.setMerchant_id(r.getMerchant_id());
			
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			
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
			
			PCCresponse res = restTemplate.postForObject("http://localhost:8009/request/checkRequest", entity, PCCresponse.class);
			if(res.isCardauthentication()) {
				if(res.isTransactionauthorization()) {
					
					logger.info("Method: checkcard -> Payment from this card, PAN is '"+card.getPan()+"', to Merchant '"+r.getMerchant_id()+"' has been performed successfully!");
					PCCrequest rt = pccrequestRepository.findByAccourerOrderIdEquals(res.getAcquirer_order_id());
					Merchant magazin = bankRepository.findByMerchantIdEquals(rt.getMerchant_id());
					
					BankAccount bankac = magazin.getBankaccount();
					bankac.setSum(bankac.getSum()+rt.getAmount());
					bankAccountRepository.save(bankac);
					
					rt.setIspayment(true);
					pccrequestRepository.save(rt);
					
					
					Map<String, Object> mapa = new HashMap<>();
					mapa.put("status", "paid");
					mapa.put("type", "bank");
					mapa.put("currency", "EUR");
					mapa.put("amount", r.getAmount());
					mapa.put("merchant", magazin.getMerchant_id());
					
					String reportDate = formatter.format(now);
					mapa.put("time",reportDate );
					
					
					HttpHeaders h = new HttpHeaders();
					
					HttpEntity<Map<String, Object>> e = new HttpEntity<Map<String, Object>>(mapa, h);
					
					String re = restTemplate.postForObject("http://localhost:8051/objectpayment/successpayment/" + r.getMerchant_order_id(), e, String.class);
					
					
					
					
					
					
					
					
					//String result = restTemplate.getForObject("http://localhost:8051/objectpayment/successpayment/" + r.getMerchant_order_id(), String.class);

					return r.getSuccessurl();
				}else {
					logger.info("Method: checkcard -> Payment for this card, PAN is '"+card.getPan()+"' can't be made");
					System.out.println("transakcija nije uspeno izvrsena na banci kupca");
					return r.getFailedurl();
				}
			}else {
				logger.info("Method: checkcard -> Card data, PAN is '"+card.getPan()+"', isn't valid!");
				System.out.println("Kartica nije validna");
				return "podaci o kartici nisu ispravni";
			}
			
		}
	}
	
	
	
	//provera se kartica koja je stigla od druge banke preko PCC
	public PCCresponse checkPCCrequest(PCCrequest card) {
			
		PCCresponse pccresponse = new PCCresponse();
		List<Card> cards = cardRepository.findAll();
			
		for(int i=0; i<cards.size(); i++) {
			if(cards.get(i).getPan().equals(card.getPan()) && cards.get(i).getSecuritycode().equals(card.getSecuritycode())
					&& cards.get(i).getCardholdername().equals(card.getCardholdername())) {
				
				String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					
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
					
					Card c = cardRepository.findByPanEquals(card.getPan());
					BankAccount ba = c.getBankaccount();
					
					if(card.getAmount()<=ba.getSum()) {
						
						ba.setSum(ba.getSum()-card.getAmount());
						bankAccountRepository.save(ba);
						logger.info("Method: checkPCCrequest -> A card, PAN is '"+card.getPan()+"',  is reserved for "+card.getAmount()+" euros for payment");
						pccresponse.setTransactionauthorization(true);
						pccresponse.setAcquirer_order_id(card.getAcquirer_order_id());
						pccresponse.setAcquirer_timestamp(card.getAcquirer_timestamp());
						long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
						pccresponse.setIssuer_order_id(number);
						pccresponse.setIssuer_timestamp(now);
						pccresponseRepository.save(pccresponse);
						return pccresponse;
					}		
				}
				else {
					logger.info("Method: checkPCCrequest -> Card data, PAN is '"+card.getPan()+"', isn't valid!");
					System.out.println("KARTICA KUPCA NIJE VALIDNA ZBOG DATUMA");
				}	
			}
			else {
				logger.info("Method: checkPCCrequest -> Card data, PAN is '"+card.getPan()+"', isn't valid!");
				System.out.println("KARTICA KUPCA NIJE VALIDNA ZBOG OSTALIH PODATAKA");
			}		
		}
		return pccresponse;
	}
	
	
}
