package com.cosmetics.myshop.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cosmetics.myshop.dto.LoginResponseDTO;
import com.cosmetics.myshop.dto.RegisterDTO;
import com.cosmetics.myshop.service.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {
	@Autowired
	AuthenticationService authenticationService;
	
	@PostMapping("/register")
	@ResponseBody
	public RegisterDTO register(@RequestBody RegisterDTO body) {
		return authenticationService.registerUser(body);	
	}
	
	@PostMapping("/login")
	public String login(HttpServletResponse response, @RequestParam Map<String, String> body, RedirectAttributes attributes) {
//		System.out.println("post ./auth/login");
		return authenticationService.loginUser(response, body, attributes);
	}


}
