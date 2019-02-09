package com.example.Bank.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Bank.dto.DataLoaderComponent;
import com.example.Bank.dto.PCCrequestDTO;
import com.example.Bank.dto.RequestDTO;
import com.example.Bank.model.Merchant;
import com.example.Bank.model.PCCrequest;
import com.example.Bank.model.PCCresponse;
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
	
	@Autowired
	private DataLoaderComponent dataLoaderComponent;
	
	private Logger logger = Logger.getLogger(RequestController.class);
	
	@PostMapping("/checkrequest")
	public String checkRequest(@RequestBody RequestDTO requestdto){
		
		
		Merchant merchant = bankRepository.findByMerchantIdEquals(requestdto.getMerchant_id());
		
		if(merchant!=null) {
			if(requestdto.getMerchant_password().equals(merchant.getMerchant_password())) {
				System.out.println("Pronasao merchanta!!!!!!!!!");
				logger.info("Method: checkRequest -> Merchant '"+ requestdto.getMerchant_id()+"' was successfully found !");
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
				return "http://" + dataLoaderComponent.getIp() + ":8097/index.html/id=" + req.getId();
			}
			else {
				logger.error("Method: checkrequest -> Merchant '"+ requestdto.getMerchant_id()+"' was unsuccessfully found !");
			}
		}
		else {
			logger.error("Method: checkrequest -> Merchant '"+ requestdto.getMerchant_id()+"' was unsuccessfully found !");
		}
		
		
		return "neuspesno";
		
	}
	
	
	//proverava zahtev koji je stigao iz Banke B preko PCC
	@PostMapping("/checkPCCrequest")
	public PCCresponse checkPCCrequest(@RequestBody PCCrequest pcc) {
			
		PCCresponse response = cardService.checkPCCrequest(pcc);
		

		return response;
	}
	
	
}
