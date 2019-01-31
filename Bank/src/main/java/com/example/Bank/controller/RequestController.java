package com.example.Bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Bank.dto.PCCrequestDTO;
import com.example.Bank.dto.RequestDTO;
import com.example.Bank.model.Merchant;
import com.example.Bank.repository.BankRepository;
import com.example.Bank.service.CardService;

@RestController
@RequestMapping("/request")
public class RequestController {

	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private CardService cardService;
	
	@PostMapping("/checkrequest")
	public String checkRequest(@RequestBody RequestDTO requestdto){
		
		System.out.println("DOSAOOOOO U KONTROLER BANKE, MERCHANT_ID=" + requestdto.getMerchant_id());
		
		Merchant merchant = bankRepository.findByMerchantIdEquals(requestdto.getMerchant_id());
		
		if(merchant!=null) {
			if(requestdto.getMerchant_password().equals(merchant.getMerchant_password())) {
				System.out.println("Pronasao merchanta!!!!!!!!!");
				return "http://localhost:8097/index.html";
			}
		}
		
		
		return "neuspesno";
		
	}
	
	
	//proverava zahtev koji je stigao iz Banke B preko PCC
	@PostMapping("/checkPCCrequest")
	public String checkPCCrequest(@RequestBody PCCrequestDTO pcc) {
			
		String response = cardService.checkPCCrequest(pcc);
		

		return "dosao u banku a";
	}
	
	
}
