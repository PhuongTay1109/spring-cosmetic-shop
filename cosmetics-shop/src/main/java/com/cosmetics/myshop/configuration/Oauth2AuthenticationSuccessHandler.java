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
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.cosmetics.myshop.model.Role;
import com.cosmetics.myshop.model.User;
import com.cosmetics.myshop.repository.RoleRepository;
import com.cosmetics.myshop.repository.UserRepository;
import com.cosmetics.myshop.service.AuthenticationService;
import com.cosmetics.myshop.service.RoleService;
import com.cosmetics.myshop.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	UserService userService;
	

	@Autowired
	RoleService roleService;
	
	private String determineOAuth2Provider(Map<String, Object> attributes) {
	    // Check if specific attributes exist to identify the OAuth2 provider
	    if (attributes.containsKey("sub")) {
	        // Google-specific attribute
	        return "GOOGLE";
	    } else if (attributes.containsKey("id")) {
	        // Facebook-specific attribute
	        return "FACEBOOK";
	    }
	    // Add more checks for other providers if necessary

	    // Default to "Unknown" if provider cannot be determined
	    return "Unknown";
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		Map<String, Object> attributes = oAuth2User.getAttributes();
		String email = (String) attributes.get("email");
		Optional<User> optionalUser = userService.findByEmail(email);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			// Create authentication token for the user
			UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		} else {
			String avatar = (String) attributes.get("picture") != null ? (String) attributes.get("picture")
					: "/img/user/no_avatar.png";
			String provider = determineOAuth2Provider(attributes);
			String firstName = (String) attributes.get("given_name");
			String lastName = (String) attributes.get("family_name");
			if (attributes.get("name") != null) { 
				String fullName = (String) attributes.get("name");
				firstName = fullName.split(" ")[0];
				lastName = fullName.split(" ")[1];
			}
			String username = email;
			String password = "";
			String address = "";
			String phone = "";
			Set<Role> roles = new HashSet<>();
			roles.add(roleService.findByAuthority("ROLE_USER").orElseThrow());
			User user = new User(username, password, firstName, lastName, phone, email, avatar, address, provider,
					roles);
			userService.saveUser(user);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,
					null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		}
		response.sendRedirect("/");
	}

}
