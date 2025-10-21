package com.example.userservice.user.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtValidationFilter extends OncePerRequestFilter {

	private final JwtUtility jwtUtility;

	public JwtValidationFilter(JwtUtility jwtUtility) {
		this.jwtUtility = jwtUtility;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		System.err.println("enter user service ="+request.getRequestURI().toString());
		
		 if (request.getRequestURI().toString().startsWith("/auth/")) {
		        filterChain.doFilter(request, response);
		        return;
		    }
		 
		String authHeader = request.getHeader("Authorization");

		String token = authHeader.substring(7);
		String username = "";

		List<SimpleGrantedAuthority> roles = null;

        // Option 1: Use JWT from header if present
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
         
            try {
                username = jwtUtility.extractUsernameFromToken(token);
                roles = jwtUtility.getRoles(token);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        }
		
		 // Option 2: If Gateway forwarded username/roles headers, use them
        if (username == null) {
        	System.err.println("request header = "+request.getHeader("X-Auth-Roles"));
            username = request.getHeader("X-Auth-User");
            String rolesHeader = request.getHeader("X-Auth-Roles");
            if (username != null && rolesHeader != null) {
                roles = Arrays.stream(rolesHeader.split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList();
            }
        }

     // Set Spring Security context if authentication is missing
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	roles = jwtUtility.getRoles(token);
            UserDetails userDetails = new User(username, "", roles != null ? roles : List.of());
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

		filterChain.doFilter(request, response);
	}

}
