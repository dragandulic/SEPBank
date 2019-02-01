package com.example.BankB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BankB.dto.PCCrequestDTO;
import com.example.BankB.model.Merchant;
import com.example.BankB.model.PCCrequest;
import com.example.BankB.model.Request;
import com.example.BankB.repository.BankRepository;
import com.example.BankB.repository.RequestRepository;
import com.example.BankB.service.CardService;

@RestController
@RequestMapping("/request")
public class RequestController {
	
	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private RequestRepository requestRepository;
	
	@PostMapping("/checkrequest")
	public String checkRequest(@RequestBody Request request) {
		
		Merchant merchant = bankRepository.findByMerchantIdEquals(request.getMerchant_id());
		
		if(merchant!=null) {
			if(merchant.getMerchant_password().equals(request.getMerchant_password())){
				System.out.println("Pronasao merchanta u BANK B !!!!!!!!");
				Request r =requestRepository.save(request);
				
				//stranica na koju se redirektuje
				return "http://localhost:8010/index.html/id="+r.getId();
			}
		}
		
		return "neuspesno";
		
	}
	
	//proverava zahtev koji je stigao iz Banke A preko PCC
	@PostMapping("/checkPCCrequest")
	public String checkPCCrequest(@RequestBody PCCrequest pcc) {
		
		String response = cardService.checkPCCrequest(pcc);
		
		
		return "dosao u banku b";
	}

}
