package com.cosmetics.myshop.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cosmetics.myshop.dto.RegisterDTO;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {
	@PostMapping("/register")
	@ResponseBody
	public RegisterDTO register(@RequestBody RegisterDTO body) {
		System.out.println(body);
		boolean isValid = false;
		
		//Validate first name
		boolean isFirstNameValid = Pattern.compile("^[A-Z][a-zA-Z]*$").matcher(body.getFirstName()).matches();
		boolean isLastNameValid = Pattern.compile("^[A-Z][a-zA-Z]*$").matcher(body.getLastName()).matches();
		boolean isUsernameValid = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z]).{4,}$").matcher(body.getUsername()).matches();
		boolean isPasswordValid = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$").matcher(body.getPassword()).matches();
		boolean isPhoneValid = Pattern.compile("^[0-9]{10}$").matcher(body.getPhone()).matches();
		if (isFirstNameValid == false) {
			body.setFirstName("The fist name must only contain letters and the first letter must be capitalized!");
			isValid = false;
		}
		if (isLastNameValid == false) {
			body.setLastName("The last name must only contain letters and the first letter must be capitalized!");
			isValid = false;
		}
		if (isUsernameValid == false) {
			body.setUsername("Username must contain at least 4 characters, including letters and numbers!");
			isValid = false;
		}
		if (isPasswordValid == false) {
			body.setPassword("Password must contain at least 6 characters, including letters and numbers!");
			isValid = false;
		}
		if (isPhoneValid == false) {
			body.setPhone("Phone must contain only 10 numbers!");
			isValid = false;
		}
		body.setValid(isValid);
		if (isValid == false) {
			System.out.println(body.toString());
			return body;
		}
		
		
		
		
		return null;
	}
}
