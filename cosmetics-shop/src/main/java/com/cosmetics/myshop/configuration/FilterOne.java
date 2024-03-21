package com.cosmetics.myshop.configuration;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FilterOne extends OncePerRequestFilter {
	private RequestMatcher requestMatcher;
	public FilterOne(String pattern) {
        // Create an AntPathRequestMatcher with the specified pattern
        this.requestMatcher = new AntPathRequestMatcher(pattern);
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("Authentication: " + authentication);
		if (authentication!=null) {
			System.out.println(authentication.getAuthorities());
			String roles = authentication.getAuthorities()
					.stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.joining(""));
			System.out.println(roles);
		}
		System.out.println("FIlter One: " + request.getRequestURI() );
		if (requestMatcher.matches(request)) {
            // Process the request
            System.out.println("Filter One processing request for pattern: " + request.getServletPath());
            // Continue the filter chain
            filterChain.doFilter(request, response);
        } else {
            // If the request does not match the pattern, continue to the next filter
            filterChain.doFilter(request, response);
        }
	}

}
