package com.cosmetics.myshop.configuration;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.cosmetics.myshop.model.Role;
import com.cosmetics.myshop.model.User;
import com.cosmetics.myshop.repository.RoleRepository;
import com.cosmetics.myshop.repository.UserRepository;
import com.cosmetics.myshop.service.AuthenticationService;
import com.cosmetics.myshop.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	UserService userService;
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
		Map<String, Object> attributes = oAuth2User.getAttributes();
		System.out.println(attributes);
		String email = (String)attributes.get("email");
		System.out.println(email);
		Optional<User> optionalUser = userService.findByEmail(email);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			 // Create authentication token for the user
			UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			response.sendRedirect("/");
		} else {

//			public User(String username, String password, String firstName, String lastName, String phone,
//					String email, String avatar, String address, String provider, Set<Role> authorities) {
			String avatar = (String)attributes.get("picture");
			String firstName = (String)attributes.get("given_name");
			String lastName = (String)attributes.get("family_name");
			String username = email;
			String password = "";
			String address = "";
			String phone = "";
			Set<Role> roles = new HashSet<>();
			roles.add(roleRepository.findByAuthority("USER").orElseThrow());
			User user = new User(username,password, firstName, lastName, phone,email,avatar,address,"OAUTH2.0",roles);
			userService.saveUser(user);
			response.sendRedirect("/");
		}
		
		
	}

}
