package com.cosmetics.myshop.service;



import com.cosmetics.myshop.dto.RegisterDTO;

public interface AuthenticationService {

	public RegisterDTO registerUser(RegisterDTO body);
	
}
