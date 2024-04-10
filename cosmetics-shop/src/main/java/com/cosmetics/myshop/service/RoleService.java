package com.cosmetics.myshop.service;

import java.util.Optional;


import com.cosmetics.myshop.model.Role;



public interface RoleService {
	
	public Optional<Role> findByAuthority(String authority);


}
