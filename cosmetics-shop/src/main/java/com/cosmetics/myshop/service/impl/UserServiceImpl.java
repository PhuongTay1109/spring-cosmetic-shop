package com.cosmetics.myshop.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cosmetics.myshop.model.User;
import com.cosmetics.myshop.repository.UserRepository;
import com.cosmetics.myshop.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).get();
        if(user == null){
            throw new UsernameNotFoundException("could not found user..!!");
        }
        return user;
    }
	
	public Optional<User> findByUserId(Integer userId) {
		return userRepository.findByUserId(userId);
	}
	
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Map<String, Object> updateProfile(Map<String,String> body) {
		Map<String, Object> response = new HashMap<>();
//		boolean isPhoneValid = Pattern.compile("^[0-9]{10}$").matcher(body.getPhone()).matches();
		boolean isValid = true;
//		System.out.println(body);
		// Validate first name
		Integer userId = Integer.parseInt(body.get("userId"));
		String firstName = body.get("firstName");
		String lastName = body.get("lastName");
		String address = body.get("address");
		String phone = body.get("phone");
		
		boolean isFirstNameValid = Pattern.compile("^[A-ZÀ-Ỹ][a-yà-ỹ]*$").matcher(firstName).matches();
		boolean isLastNameValid = Pattern.compile("^[A-ZÀ-Ỹ][a-yà-ỹ]*$").matcher(lastName).matches() 
				|| lastName.equals("");
		//Last name can be empty
		boolean isPhoneValid = Pattern.compile("^[0-9]{10}$").matcher(phone).matches();
		if (isFirstNameValid == false) {
			response.put("firstName", "The fist name must only contain letters and the first letter must be capitalized!");
			isValid = false;
		}
		if (isLastNameValid == false) {
			response.put("lastName", "The fist name must only contain letters and the first letter must be capitalized!");
			isValid = false;
		}
		if (isPhoneValid == false) {
			response.put("phone", "Phone must contain only 10 numbers!");
			isValid = false;
		}
		response.put("isValid",isValid);
		if (isValid) {
			User existingUser = findByUserId(userId).get();
			existingUser.setFirstName(firstName);
			existingUser.setLastName(lastName);
			existingUser.setPhone(phone);
			existingUser.setAddress(address);
			saveUser(existingUser);
			Authentication authentication = new UsernamePasswordAuthenticationToken((User)existingUser,null, existingUser.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		return response;
	}

	@Override
	public Map<String, Object> changePassword(Map<String, String> body, User user) {
		Map <String, Object> response = new HashMap<>();
		boolean isNewPasswordValid = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$").matcher(body.get("newPassword"))
				.matches();
		boolean isValid = true;
		if (!passwordEncoder.matches(body.get("currentPassword"), user.getPassword())){
			response.put("currentPassword", "Current password isn't correct!");
			isValid = false;
		}
		if (body.get("newPassword").equals(body.get("currentPassword"))) { //New password is as the same as current password
			response.put("newPassword", "New password must be different from current password!");
			isValid = false;
		} else if(!isNewPasswordValid) {
			response.put("newPassword", "New password must contain at least 6 characters, including letters and numbers!");
			isValid = false;
		}
		if (!body.get("confirmPassword").equals(body.get("newPassword"))) { //Confirm password doesn't match new password
			response.put("confirmPassword","Confirm password must match new password!");
			isValid = false;
		}
		response.put("isValid", isValid);
		if (isValid) {
			String encodedPassword = passwordEncoder.encode(body.get("confirmPassword"));
			user.setPassword(encodedPassword);
			saveUser(user);
		}
		return response;
	}

	@Override
	public synchronized void editImage(String newAvatar, User user)  throws Exception {
		final String IMAGE_UPLOAD_DIRECTORY = "src/main/resources/static";
		if (!user.getAvatar().equals("/img/user/no_avatar.png") && user.getAvatar().contains("/img/user")) { // If not default avatar, then delete it
			Path filePath = Paths.get(IMAGE_UPLOAD_DIRECTORY, user.getAvatar());
			Files.deleteIfExists(filePath);
			
		}
		user.setAvatar(newAvatar);
		userRepository.save(user);
		
	}

	

}
