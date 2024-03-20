package com.cosmetics.myshop.service;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
	@Autowired
	private JwtEncoder jwtEncoder;

	@Autowired
	private JwtDecoder jwtDecoder;

	public String generateJwt(Authentication auth) {
		Instant now = Instant.now();

//		In the code snippet you provided, auth.getAuthorities() likely returns a collection (possibly a Set or a List) of GrantedAuthority objects. 
//		By invoking stream() on this collection, 
//		you convert it into a stream, which enables 
//		you to apply functional-style operations like 
//		map() to transform each element in the stream 
//		as needed.
		String scope = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(" "));
		System.out.println("authName ");
		JwtClaimsSet claims = JwtClaimsSet.builder().issuer("self").issuedAt(now).subject(auth.getName())
				.claim("roles", scope).build();
		return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}

	public Jwt decodeJwt(String token) {
		Jwt asd = jwtDecoder.decode(token);
		System.out.println(asd.getClaims());
		return jwtDecoder.decode(token);
	}
	
}
