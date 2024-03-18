package com.cosmetics.myshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/register")
	private String register() {
		return "security/register";
	}
	
	@GetMapping("/login")
	private String login() {
		System.out.println("Asdas");
		return "/security/login";
	}
}
