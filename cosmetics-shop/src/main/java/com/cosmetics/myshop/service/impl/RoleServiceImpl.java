package com.cosmetics.myshop.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosmetics.myshop.model.Role;
import com.cosmetics.myshop.repository.RoleRepository;
import com.cosmetics.myshop.service.RoleService;


@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	RoleRepository roleRepository;
	
	public Optional<Role> findByAuthority(String authority) {
		return roleRepository.findByAuthority(authority);
	}


}
