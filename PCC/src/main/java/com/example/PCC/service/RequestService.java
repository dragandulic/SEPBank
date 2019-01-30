package com.example.PCC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.PCC.model.Bank;
import com.example.PCC.model.Request;
import com.example.PCC.repository.BankRepository;

@Service
public class RequestService {

	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private RestTemplate restTemplate; 
	
	public String checkReq(Request request) {
		
		List<Bank> banks = bankRepository.findAll();
		
		String[] delovi = request.getPan().split(" ");
		String deo = delovi[0].substring(0, Math.min(delovi[0].length(), 3));
		
		for(int i=0; i<banks.size(); i++) {
			
			if(deo.equals(banks.get(i).getBanknumber())) {
				System.out.println("nasao banku");
				
				HttpHeaders header = new HttpHeaders();
				HttpEntity entity = new HttpEntity(request, header);
				
				//String response = restTemplate.postForObject(banks.get(i).getBankserviceurl(), entity, String.class);
			}
		}
		
		
		
		return null;
	}
	
}
