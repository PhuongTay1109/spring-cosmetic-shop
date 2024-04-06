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
	public Map<String, Object> getProfile(@RequestBody Map<String, String> body) {
		Map<String, Object> response = new HashMap<>();
//		boolean isPhoneValid = Pattern.compile("^[0-9]{10}$").matcher(body.getPhone()).matches();
		boolean isValid = true;
		System.out.println(body);
		// Validate first name
		Integer userId = Integer.parseInt(body.get("userId"));
		String firstName = body.get("firstName");
		String lastName = body.get("lastName");
		String phone = body.get("phone");
		response.put("firstName", firstName);
		response.put("lastName", lastName);
		response.put("phone", phone);
		
		boolean isFirstNameValid = Pattern.compile("^[A-ZÀ-Ỹ][a-yà-ỹ]*$").matcher(firstName).matches();
		boolean isLastNameValid = Pattern.compile("^[A-ZÀ-Ỹ][a-yà-ỹ]*$").matcher(lastName).matches();
		boolean isPhoneValid = Pattern.compile("^[0-9]{10}$").matcher(phone).matches();
		if (isFirstNameValid == false) {
			response.put("firstName", "The fist name must only contain letters and the first letter must be capitalized!");
			isValid = false;
		}
		if (isLastNameValid == false) {
			response.put("lastName", "The fist name must only contain letters and the first letter must be capitalized!");
			isValid = false;
		}
		if (isPhoneValid == false) {
			response.put("phone", "Phone must contain only 10 numbers!");
			isValid = false;
		}
		response.put("isValid",isValid);
		if (isValid) {
			User existingUser = userService.findByUserId(userId).get();
			existingUser.setFirstName(firstName);
			existingUser.setLastName(lastName);
			existingUser.setPhone(phone);
			userService.saveUser(existingUser);
			Authentication authentication = new UsernamePasswordAuthenticationToken((User)existingUser,null, existingUser.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		return response;
	}
	
	@GetMapping("/password/change")
	public String getPasswordChange() {
		return "user/password_change";
	}
	
	@ResponseBody
	@PutMapping("/password/change")
	public Map<String, Object> handlePasswordChange(@RequestBody Map<String, String> body, Authentication authentication) {
		//TODO: process PUT request
		Map <String, Object> response = new HashMap<>();
		User user = (User)authentication.getPrincipal();
		boolean isNewPasswordValid = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$").matcher(body.get("newPassword"))
				.matches();
		boolean isValid = true;
		if (!passwordEncoder.matches(body.get("currentPassword"), user.getPassword())){
			response.put("currentPassword", "Current password isn't correct!");
			isValid = false;
		}
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
			String encodedPassword = passwordEncoder.encode(body.get("confirmPassword"));
			user.setPassword(encodedPassword);
			userService.saveUser(user);
		}
		return response;
	}
}
