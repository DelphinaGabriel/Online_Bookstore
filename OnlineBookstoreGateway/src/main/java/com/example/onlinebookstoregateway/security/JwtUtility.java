package com.example.onlinebookstoregateway.security;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JwtUtility {

	private final String SECRET_KEY = "ONLINE_BOOK_STORE";
	
	public String extractUsernameFromToken(String token) {
		return JWT.decode(token).getSubject();
	}
	
	public DecodedJWT getClaims(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
        } catch (JWTVerificationException ex) {
        	ex.printStackTrace();
            throw new JWTVerificationException("Invalid or expired JWT: " + ex.getMessage());
        }
    }

    public List<SimpleGrantedAuthority> getRoles(String token) {
        List<String> roleList = JWT.decode(token).getClaim("roles").asList(String.class);
        return roleList.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public Boolean isTokenExpired(String token) {
        return getClaims(token).getExpiresAt().before(new Date());
    }

    public Boolean isTokenValidate(String token) {
        try {
            getClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
        	e.printStackTrace();
            return false;
        }
    }
	
}
