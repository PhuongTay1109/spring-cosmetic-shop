package com.cosmetics.myshop.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cosmetics.myshop.dto.RegisterDTO;
import com.cosmetics.myshop.model.Role;
import com.cosmetics.myshop.model.User;
import com.cosmetics.myshop.service.AuthenticationService;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	@Autowired
	UserServiceImpl userService;
	
	@Autowired
	RoleServiceImpl roleService;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtServiceImpl jwtService;

	

	public RegisterDTO registerUser(RegisterDTO body) {
		System.out.println(body);
		boolean isValid = true;

		// Validate first name
		boolean isFirstNameValid = Pattern.compile("^[A-ZÀ-Ỹ][a-yà-ỹ]*$").matcher(body.getFirstName()).matches();
		boolean isLastNameValid = Pattern.compile("^[A-ZÀ-Ỹ][a-yà-ỹ]*$").matcher(body.getLastName()).matches();
		boolean isUsernameValid = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z]).{4,}$").matcher(body.getUsername())
				.matches();
		boolean isPasswordValid = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$").matcher(body.getPassword())
				.matches();
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
		Optional<User> user = userService.findByUsername(body.getUsername());
		if (user.isPresent()) {
			body.setUsername("Username has been used!");
			isValid = false;
		}
		user = userService.findByEmail(body.getEmail());
		if (user.isPresent()) {
			body.setEmail("Email has been used!");
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
		if (isValid) { // Valid
			String encodedPassword = passwordEncoder.encode(body.getPassword());
			Set<Role> roles = new HashSet<>();
			roles.add(roleService.findByAuthority("USER").orElseThrow());
			User registeredUser = new User(body.getUsername(), encodedPassword, body.getFirstName(), body.getLastName(),
					body.getPhone(), body.getEmail(), "/img/user/no_avatar.png", body.getAddress(), "LOCAL", roles);
			userService.saveUser(registeredUser);
			RegisterDTO successRegister = new RegisterDTO();
			successRegister.setValid(true);
			return successRegister;
		}
		return body;
	}
	
}
