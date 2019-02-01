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
import com.example.Bank.model.Request;
import com.example.Bank.repository.BankRepository;
import com.example.Bank.repository.RequestRepository;
import com.example.Bank.service.CardService;

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
	public String checkRequest(@RequestBody RequestDTO requestdto){
		
		System.out.println("DOSAOOOOO U KONTROLER BANKE, MERCHANT_ID=" + requestdto.getMerchant_id());
		
		Merchant merchant = bankRepository.findByMerchantIdEquals(requestdto.getMerchant_id());
		
		if(merchant!=null) {
			if(requestdto.getMerchant_password().equals(merchant.getMerchant_password())) {
				System.out.println("Pronasao merchanta!!!!!!!!!");
				Request r = new Request();
				r.setAmount(requestdto.getAmount());
				r.setMerchant_id(requestdto.getMerchant_id());
				r.setMerchant_password(requestdto.getMerchant_password());
				r.setMerchant_order_id(requestdto.getMerchant_order_id());
				r.setSuccessurl(requestdto.getSuccessurl());
				r.setErrorurl(requestdto.getErrorurl());
				r.setFailedurl(requestdto.getFailedurl());
				r.setMerchant_order_id(requestdto.getMerchant_order_id());
				Request req = requestRepository.save(r);
				return "http://localhost:8097/index.html/id=" + req.getId();
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
