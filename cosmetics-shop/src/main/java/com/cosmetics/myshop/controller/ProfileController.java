package com.cosmetics.myshop.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

@Controller
@RequestMapping("/profile")
public class ProfileController {
	@Autowired
	UserService userService;
	
	@GetMapping("")
	public String getProfile(Authentication authentication, Model model) {
		User user = (User)authentication.getPrincipal();
		model.addAttribute("user", user);
		return "user/profile.html";
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
			response.put("phone", "The fist name must only contain letters and the first letter must be capitalized!");
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
}
