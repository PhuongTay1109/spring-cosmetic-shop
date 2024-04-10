package com.cosmetics.myshop.service;



import java.util.Map;

import com.cosmetics.myshop.dto.RegisterDTO;

public interface AuthenticationService {

	public Map<String, Object> registerUser(RegisterDTO body);
	
}
