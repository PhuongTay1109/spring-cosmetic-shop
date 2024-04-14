package com.cosmetics.myshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cosmetics.myshop.dto.RegisterDTO;
import com.cosmetics.myshop.model.User;

public interface UserService extends UserDetailsService {

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	public List<User> findAllUsers();
	public Optional<User> findByUserId(Integer userId) ;
	public Optional<User> findByEmail(String email);
	public Optional<User> findByUsername(String username);
	public User saveUser(User user);

}
