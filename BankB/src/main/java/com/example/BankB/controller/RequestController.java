package com.example.BankB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BankB.model.Merchant;
import com.example.BankB.model.Request;
import com.example.BankB.repository.BankRepository;

@RestController
@RequestMapping("/request")
public class RequestController {
	
	@Autowired
	private BankRepository bankRepository;
	
	@PostMapping("/checkrequest")
	public String checkRequest(@RequestBody Request request) {
		System.out.println(request.getMerchant_id());
		System.out.println(request.getMerchant_password());
		Merchant merchant = bankRepository.findByMerchantIdEquals(request.getMerchant_id());
		
		if(merchant!=null) {
			if(merchant.getMerchant_password().equals(request.getMerchant_password())){
				System.out.println("Pronasao merchanta u BANK B !!!!!!!!");
				//stranica na koju se redirektuje
				return "http://localhost:8010/index.html";
			}
		}
		
		return "neuspesno";
		
	}

}
