package com.cosmetics.myshop.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cosmetics.myshop.dto.LoginDTO;
import com.cosmetics.myshop.dto.RegisterDTO;
import com.cosmetics.myshop.service.AuthenticationService;

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
	public String login(@RequestParam Map<String, String> body, RedirectAttributes attributes) {
		return authenticationService.loginUser(body, attributes);
//		return "home";
	}


}
