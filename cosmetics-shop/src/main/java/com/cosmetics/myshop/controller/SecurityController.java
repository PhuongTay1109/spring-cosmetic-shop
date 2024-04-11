package com.cosmetics.myshop.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
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
		String provider = user.get().getProvider();
		if (!provider.equals("LOCAL")) {
			return "redirect:/forgot_password?error";
		}
		PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetToken(user.get());
		passwordResetTokenService.sendResetEmail(email, passwordResetToken.getToken());
		return "redirect:/forgot_password?success";
	}
	
	@GetMapping("/reset_password")
	public String getResetPassword(@RequestParam Map<String, String> body, Model model ) {
	 	String token = body.get("token");
		if (!passwordResetTokenService.validatePasswordResetToken(token)) {
	 		return "redirect:/forgot_password?invalid_token";
	 	}
	 	PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token).get();
	 	User user = passwordResetToken.getUser();
	 	System.out.println(user);
	 	model.addAttribute("userId", user.getUserId());
		return "security/reset_password";
	}
	
	@ResponseBody
	@PutMapping("/reset_password")
	public Map<String, Object> processResetPassword(@RequestBody Map<String, String> body) {
		Map<String, Object> response = new HashMap<>();
		System.out.println(body);
		boolean isValid= true;
		boolean isNewPasswordValid = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$").matcher(body.get("newPassword"))
				.matches();
		if (body.get("newPassword").equals(body.get("currentPassword"))) { //New password is as the same as current password
			response.put("newPassword", "New password must be different from current password!");
			isValid = false;
		} else if(!isNewPasswordValid) {
			response.put("newPassword", "New password must contain at least 6 characters, including letters and numbers!");
			isValid = false;
		}
		if (!body.get("confirmPassword").equals(body.get("newPassword"))) { //Confirm password doesn't match new password
			response.put("confirmPassword","Confirm password must match new password!");
			isValid = false;
		}
		response.put("isValid", isValid);
		if (isValid) {
			Integer id = Integer.parseInt(body.get("userId"));
			User existingUser = userService.findByUserId(id).orElseThrow();
			String encodedPassword = passwordEncoder.encode(body.get("confirmPassword"));
			existingUser.setPassword(encodedPassword);
			//Delete token
			userService.saveUser(existingUser);
		}
		return response;
		
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
