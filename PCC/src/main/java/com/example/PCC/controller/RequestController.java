package com.example.PCC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PCC.model.Request;
import com.example.PCC.model.Response;
import com.example.PCC.service.RequestService;

@RestController
@RequestMapping("/request")
public class RequestController {
	
	@Autowired
	private RequestService requestService;
	
	@PostMapping("/checkRequest")
	public Response checkRequest(@RequestBody Request request) {
		
		Response response = requestService.checkReq(request);
		
		return response;
	}
	
	@PostMapping("/forwardRequest")
	public void forwardRequest(@RequestBody Request request) {
		
	}

}
