package com.example.PCC.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import com.example.PCC.model.Bank;
import com.example.PCC.model.Request;
import com.example.PCC.model.Response;
import com.example.PCC.repository.BankRepository;

@Service
public class RequestService {

	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private RestTemplate restTemplate; 
	
	private Logger logger = Logger.getLogger(RequestService.class);
	
	public Response checkReq(Request request) {
		
		List<Bank> banks = bankRepository.findAll();
		
		String[] delovi = request.getPan().split(" ");
		String deo = delovi[0].substring(0, Math.min(delovi[0].length(), 3));
		
		Response response = new Response();
		boolean correctpan = true;
		
		for(int i=0; i<banks.size(); i++) {
			
			if(deo.equals(banks.get(i).getBanknumber())) {
				
				logger.info("Method: checkcard -> Buyer's bank number is "+banks.get(i).getBanknumber()+" and it was successfully found!");
				HttpHeaders header = new HttpHeaders();
				HttpEntity entity = new HttpEntity(request, header);
				
				response = restTemplate.postForObject(banks.get(i).getBankserviceurl(), entity, Response.class);
				correctpan=true;
				break;
			}
			else {
				logger.info("Method: checkcard -> Buyer's bank number "+banks.get(i).getBanknumber()+" doesn't exist");
				correctpan=false;
			}
		}

		return response;
	
	}
	
}
