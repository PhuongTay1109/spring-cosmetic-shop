package com.cosmetics.myshop.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosmetics.myshop.model.User;

@Controller
@RequestMapping("/profile")
public class ProfileController {
	@GetMapping("")
	public String getProfile(Authentication authentication, Model model) {
		User user = (User)authentication.getPrincipal();
		System.out.println(user);
		model.addAttribute("user", user);
		return "user/profile.html";
	}
	
	@PutMapping("")
	public String getProfile(Map<String, Object> body) {
		
		return "";
	}
}
