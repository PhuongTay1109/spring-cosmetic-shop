package com.cosmetics.myshop.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cosmetics.myshop.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByAuthority(String authority);
}
