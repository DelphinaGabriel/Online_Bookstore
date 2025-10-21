package com.example.userservice.user.security;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.userservice.user.entity.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtility {
	
	private final String SECRET_KEY = "ONLINE_BOOK_STORE";
	
	public String generatePassword(){

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "1234567890";
        String specialChars = "!@#$%^&*()-_+";
        String allChars = upperCase + lowerCase + numbers + specialChars;

        password.append(upperCase.charAt(random.nextInt(upperCase.length())));
        password.append(lowerCase.charAt(random.nextInt(lowerCase.length())));
        password.append(numbers.charAt(random.nextInt(numbers.length())));
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));

        for(int i=4;i<=12;i++){
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        List<Character> passwordCharList = password.chars().mapToObj(c-> (char)c).collect(Collectors.toList());
        Collections.shuffle(passwordCharList);
        StringBuilder securePassword = new StringBuilder();
        passwordCharList.forEach(securePassword::append);
        return securePassword.toString();
    }
	
	
	public String generateJwtTokenWithSecretKey(UserDetails userDetails) {
		List<String> roleList = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
		
		return JWT.create().withSubject(userDetails.getUsername())
				.withClaim("roles", roleList)
				.withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 ))
				.sign(Algorithm.HMAC256(SECRET_KEY));
	}
	
	public String extractUsernameFromToken(String token) {
		return JWT.decode(token).getSubject();
	}
	
	public DecodedJWT getClaims(String token) {
		try {
			return JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
		}catch(JWTVerificationException ex) {
			throw new JWTVerificationException("");
		}
	}
	
	public List<SimpleGrantedAuthority> getRoles(String token) {
		List<String> roleList =  (List<String>) JWT.decode(token).getClaim("roles").asList(String.class);
		return roleList.stream().map(role-> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
	}
	
	public Boolean isTokenExpired(String token) {
		return getClaims(token).getExpiresAt().before(new Date());
	}
	
	public Boolean isTokenValidate(String token, UserDetails userDetails) {
		String username = extractUsernameFromToken(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
	

}
