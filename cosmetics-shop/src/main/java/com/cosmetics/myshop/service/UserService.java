package com.cosmetics.myshop.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;

import com.cosmetics.myshop.model.User;

public interface UserService extends UserDetailsService {

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	public List<User> findAllUsers();
	public Optional<User> findByUserId(Integer userId) ;
	public Optional<User> findByEmail(String email);
	public Optional<User> findByUsername(String username);
	public User saveUser(User user);
	public Map<String, Object> updateProfile(Map<String,String> body);
	//body includes "firstName, lastName, phone, address", response with Map<String, Object>
	public Map<String, Object> changePassword(Map<String, String> body, User user); 
	//body includes "currentPassword, newPassword, confirmPassword", response with Map<String, Object>
	public void editImage(String newAvatar, User user) throws Exception;

}
