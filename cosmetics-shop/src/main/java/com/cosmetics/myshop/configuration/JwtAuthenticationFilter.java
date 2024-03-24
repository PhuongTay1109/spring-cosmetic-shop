package com.cosmetics.myshop.configuration;

import java.io.IOException;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cosmetics.myshop.model.User;
import com.cosmetics.myshop.service.JwtService;
import com.cosmetics.myshop.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private static final String[] IGNORE = { "/css/**", "/js/**", "/img/**", "/webjars/**", "/webjarsjs",
			"/auth/**", "/login", "/register" };
	private RequestMatcher requestMatcher ;
	
	@Autowired
	UserService userService;
	
	@Autowired
	JwtService jwtService;

	public JwtAuthenticationFilter() {
		super();
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String accessToken = null;
		String username = null;
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals("accessToken")) {
					System.out.println(cookie);
					accessToken = cookie.getValue();
					System.out.println(accessToken);
				}

			}
		}
		System.out.println("JwtAuthenticationFilter: " + request.getRequestURI());
		
		if (accessToken == null) {
			response.sendRedirect("/login");
			filterChain.doFilter(request, response);
			return;
		}
		try {
			username = jwtService.extractUsername(accessToken);
			User  user = (User)userService.loadUserByUsername(username);
		} catch(Exception e) {
			response.sendRedirect("/login");
			System.out.println(e);
		}
	}

}
