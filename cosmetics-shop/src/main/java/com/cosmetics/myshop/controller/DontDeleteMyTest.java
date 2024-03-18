package com.cosmetics.myshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DontDeleteMyTest {
	
	@GetMapping("/home")
	private String home() {
		return "/user/homepage";
	}

}
