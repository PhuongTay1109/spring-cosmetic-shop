package com.cosmetics.myshop.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cosmetics.myshop.model.User;
import com.cosmetics.myshop.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequestMapping("/profile")
public class ProfileController {
	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping("")
	public String getProfile(Authentication authentication, Model model) {
		User user = (User)authentication.getPrincipal();
		model.addAttribute("user", user);
		return "user/profile";
	}
	
	@PutMapping("")
	@ResponseBody
	public Map<String, Object> processUpdateProfile(@RequestBody Map<String, String> body) {
		return userService.updateProfile(body); //Pass @RequsetBody, return Response with Map
	}
	
	@GetMapping("/password/change")
	public String getPasswordChange() {
		return "user/password_change";
	}
	
	@ResponseBody
	@PutMapping("/password/change")
	public Map<String, Object> processPasswordChange(@RequestBody Map<String, String> body, Authentication authentication) {
		//TODO: process PUT request
		User user = (User)authentication.getPrincipal();
		return userService.changePassword(body, user);
	}
}
