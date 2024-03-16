package com.cosmetics.myshop.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

	@GetMapping("/register")
	private String register() {
		return "/security/register";
	}
}
