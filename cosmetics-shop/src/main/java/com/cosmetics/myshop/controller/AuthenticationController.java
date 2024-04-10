package com.cosmetics.myshop.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cosmetics.myshop.dto.RegisterDTO;
import com.cosmetics.myshop.service.AuthenticationService;


@Controller
@RequestMapping("/auth")
public class AuthenticationController {
	@Autowired
	AuthenticationService authenticationService;
	
	@PostMapping("/register")
	@ResponseBody
	public Map<String, Object> register(@RequestBody RegisterDTO body) {
		return authenticationService.registerUser(body);	
	}


}
