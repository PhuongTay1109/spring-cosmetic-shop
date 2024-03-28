package com.cosmetics.myshop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosmetics.myshop.model.Role;
import com.cosmetics.myshop.repository.RoleRepository;


@Service
public class RoleService {
	
	@Autowired
	RoleRepository roleRepository;
	
	public Optional<Role> findByAuthority(String authority) {
		return roleRepository.findByAuthority(authority);
	}


}
