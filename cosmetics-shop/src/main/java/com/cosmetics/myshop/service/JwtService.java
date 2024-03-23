package com.cosmetics.myshop.service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

//public class JwtService {
//	@Autowired
//	private JwtEncoder jwtEncoder;
//
//	@Autowired
//	private JwtDecoder jwtDecoder;
//
//	public String generateJwt(Authentication auth) {
//		Instant now = Instant.now();
//
////		In the code snippet you provided, auth.getAuthorities() likely returns a collection (possibly a Set or a List) of GrantedAuthority objects. 
////		By invoking stream() on this collection, 
////		you convert it into a stream, which enables 
////		you to apply functional-style operations like 
////		map() to transform each element in the stream 
////		as needed.
//		String scope = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority)
//				.collect(Collectors.joining(" "));
//		System.out.println("authName ");
//		JwtClaimsSet claims = JwtClaimsSet.builder().issuer("self").issuedAt(now).subject(auth.getName())
//				.claim("roles", scope).build();
//		return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
//	}
//
//	public Jwt decodeJwt(String token) {
//		Jwt asd = jwtDecoder.decode(token);
//		System.out.println(asd.getClaims());
//		return jwtDecoder.decode(token);
//	}
//	
//}
@Service
public class JwtService {

    public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";

    private int cookieExpiry = 60*60*60;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }



    public String GenerateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }


	private String createToken(Map<String, Object> claims, String username) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + cookieExpiry*1000L);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiryDate)
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
