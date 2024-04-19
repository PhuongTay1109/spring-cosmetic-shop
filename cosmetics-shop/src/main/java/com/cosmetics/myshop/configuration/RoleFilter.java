package com.cosmetics.myshop.configuration;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cosmetics.myshop.model.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RoleFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			filterChain.doFilter(request, response);
			return;
		}
		User user = (User)authentication.getPrincipal();
		
		boolean isAdmin = user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
		String path = request.getServletPath();
//		if (isAdmin && !path.contains("/admin")) {
//			response.sendRedirect("/admin/dashboard");
//			return;
//		}
		filterChain.doFilter(request, response);
	}

}
