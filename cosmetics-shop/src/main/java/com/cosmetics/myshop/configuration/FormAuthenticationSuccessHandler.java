package com.cosmetics.myshop.configuration;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.cosmetics.myshop.model.Role;
import com.cosmetics.myshop.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FormAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		User user = (User) authentication.getPrincipal();
		System.out.println(user);
		boolean isAdmin =user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
//		boolean isUser =user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"));
		if (isAdmin) {
			response.sendRedirect("admin/dashboard");
		} else {
			response.sendRedirect("/");
		}
		
	}

}
