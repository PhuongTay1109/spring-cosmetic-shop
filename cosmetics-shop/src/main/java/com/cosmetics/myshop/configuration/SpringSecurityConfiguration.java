package com.cosmetics.myshop.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import com.cosmetics.myshop.model.User;
import com.cosmetics.myshop.service.UserService;
import com.cosmetics.myshop.service.impl.UserServiceImpl;
import com.cosmetics.myshop.utils.RSAKeyProperties;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {
	private static final String[] IGNORE = { "/css/**", "/js/**", "/img/**", "/webjars/**", "/webjarsjs",
			"/auth/**", "/login", "/register", "/favicon.ico" };
	
	
//	@Autowired
//	JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	Oauth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;
	@Autowired
	FormAuthenticationSuccessHandler formAuthenticationSuccessHandler;
	

	@Bean
	UserDetailsService userDetailsService() {
		return new UserServiceImpl();
	}

	@Autowired
	RSAKeyProperties keys;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
		daoProvider.setUserDetailsService(userDetailsService());
		daoProvider.setPasswordEncoder(passwordEncoder);
		return new ProviderManager(daoProvider);
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// Turn off query "continue" after login
		HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
		requestCache.setMatchingRequestParameterName(null);
		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> {
					auth.requestMatchers("/admin/**").hasRole("ADMIN");
					auth.requestMatchers("/profile").authenticated();
					auth.anyRequest().permitAll();
					
				})
				.oauth2Login(oauth2 -> oauth2.loginPage("/login").successHandler(oauth2AuthenticationSuccessHandler))
				.formLogin(form -> form.loginPage("/login")
						.successHandler(formAuthenticationSuccessHandler))
//				.addFilterAfter(new RoleFilter(), UsernamePasswordAuthenticationFilter.class)
				//addFIlterAfter(Filter filterOne, Class filterTwo) filterOne after filterTwo
				.requestCache(cache -> cache.requestCache(requestCache))
				.logout(logout -> logout.permitAll())
				.build();
	}
	
//	@Bean
//	JwtDecoder jwtDecoder() {
//		return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
//	}
//
//	@Bean
//	JwtEncoder jwtEncoder() {
//		JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();
//		JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
//		return new NimbusJwtEncoder(jwks);
//	}
	
	

}
