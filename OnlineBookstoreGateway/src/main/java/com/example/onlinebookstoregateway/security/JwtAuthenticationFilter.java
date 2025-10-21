package com.example.onlinebookstoregateway.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.security.core.Authentication;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements WebFilter {

	private final JwtUtility jwtUtility;

	public JwtAuthenticationFilter(JwtUtility jwtUtility) {
		this.jwtUtility = jwtUtility;
	}

	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus status) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(status);
		return response.setComplete();
	}
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		System.err.println("enter gateway");
	    String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	    	System.err.println("enter gateway authHeader");
	        String token = authHeader.substring(7);
	        if (jwtUtility.isTokenValidate(token)) {
	            String username = jwtUtility.extractUsernameFromToken(token);
	            System.err.println("enter gateway isTokenValidate");
	            List<SimpleGrantedAuthority> roles = jwtUtility.getRoles(token);

	            Authentication auth = new UsernamePasswordAuthenticationToken(username, null, roles);
	            return chain.filter(exchange)
	                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
	        }
	    }
	    return chain.filter(exchange);
	}

//	@Override
//	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//		System.err.println("enter gateway");
//		String path = exchange.getRequest().getPath().value();
//		System.err.println("path = " + path);
//		String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
//
//		// Allow public endpoints
//		if (path.contains("/auth") || authHeader == null || !authHeader.startsWith("Bearer ")) {
//			return chain.filter(exchange);
//		}
//
//		String token = authHeader.substring(7);
//		
//		System.err.println("Auth header = " + authHeader);
//		System.err.println("Token = " + token);
//		System.err.println("Token valid = " + jwtUtility.isTokenValidate(token));
//
//		if (Boolean.FALSE.equals(jwtUtility.isTokenValidate(token))) {
//			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//			return exchange.getResponse().setComplete();
//		}
//
//		String username = jwtUtility.extractUsernameFromToken(token);
//		List<SimpleGrantedAuthority> roleList = jwtUtility.getRoles(token);
//		String roles = roleList.stream()
//                .map(SimpleGrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));
//		ServerHttpRequest mutated = exchange.getRequest().mutate()
//				.header("X-Auth-User", username)
//				.header("X-Auth-Roles", roles)
//				.header("Authorization", authHeader)
//				.build();
//
//		return chain.filter(exchange.mutate().request(mutated).build());
//	}

}
