package com.cosmetics.myshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cosmetics.myshop.model.User;


public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	Optional<User> findByUserId(Integer userId);
}
