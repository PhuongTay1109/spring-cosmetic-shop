package com.cosmetics.myshop.handler;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.cosmetics.myshop.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	UserRepository userRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("Authentication " + authentication.getPrincipal());
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		Map<String, Object> map = oAuth2User.getAttributes();
		System.out.println(map);
		response.sendRedirect("/user");
		
	}

}
