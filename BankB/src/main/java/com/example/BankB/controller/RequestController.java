package com.example.BankB.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.BankB.dto.DataLoaderComponent;
import com.example.BankB.dto.PCCrequestDTO;
import com.example.BankB.model.Merchant;
import com.example.BankB.model.PCCrequest;
import com.example.BankB.model.PCCresponse;
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
	
	@Autowired
	private DataLoaderComponent dataLoaderComponent;
	
	private Logger logger = Logger.getLogger(RequestController.class);
	
	@PostMapping("/checkrequest")
	public String checkRequest(@RequestBody Request request) {
		
		Merchant merchant = bankRepository.findByMerchantIdEquals(request.getMerchant_id());
		
		if(merchant!=null) {
			if(merchant.getMerchant_password().equals(request.getMerchant_password())){
				System.out.println("Pronasao merchanta u BANK B !!!!!!!!");
				logger.info("Method: checkRequest -> Merchant '"+ request.getMerchant_id()+"' was successfully found !");
				Request r =requestRepository.save(request);
				
				//stranica na koju se redirektuje
				return "http://" + dataLoaderComponent.getIp() + ":8010/index.html/id="+r.getId();
			}
			else {
				logger.error("Method: checkrequest -> Merchant '"+ request.getMerchant_id()+"' was unsuccessfully found !");
			}
		}
		else {
			logger.error("Method: checkrequest -> Merchant '"+ request.getMerchant_id()+"' was unsuccessfully found !");
		}
		
		return "neuspesno";
		
	}
	
	//proverava zahtev koji je stigao iz Banke A preko PCC
	@PostMapping("/checkPCCrequest")
	public PCCresponse checkPCCrequest(@RequestBody PCCrequest pcc) {
		
		PCCresponse response = cardService.checkPCCrequest(pcc);
		
		
		return response;
	}

}
