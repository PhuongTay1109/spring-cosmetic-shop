package com.cosmetics.myshop.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cosmetics.myshop.model.PasswordResetToken;
import com.cosmetics.myshop.model.User;
import com.cosmetics.myshop.service.CategoryService;
import com.cosmetics.myshop.service.PasswordResetTokenService;
import com.cosmetics.myshop.service.ProductService;
import com.cosmetics.myshop.service.UserService;

import jakarta.mail.MessagingException;

@Controller
public class SecurityController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	
	@Autowired 
	UserService userService;
	
	@Autowired
	PasswordResetTokenService passwordResetTokenService;
	
	@GetMapping("/forgot_password")
	private String getForgotPassword() {
		return "security/forgot_password";
	}
	@PostMapping("/forgot_password")
	public String processForgotPassword(@RequestParam Map<String, String> body) throws UnsupportedEncodingException, MessagingException {
		String email = body.get("email");
		Optional<User> user = userService.findByEmail(email);
		if (user.isEmpty()) {
			return "redirect:/forgot_password?error";
		}
		PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetToken(user.get());
		passwordResetTokenService.sendResetEmail(email, passwordResetToken.getToken());
		return "redirect:/forgot_password?success";
	}
	
	@GetMapping("/reset_password")
	private String getResetPassword(@RequestParam("token") String token ) {
	 	Optional<PasswordResetToken> passwordResetToken = passwordResetTokenService.findByToken(token);
	 	
		return "security/reset_password";
	}
	

	@GetMapping("/register")
	private String register() {
		return "security/register";
	}

	@GetMapping("/login")
	private String login() {
		return "security/login";
	}
}
