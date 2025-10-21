package com.example.userservice.user.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

//@Configuration
//@EnableWebFluxSecurity
public class WebFluxSecurityConfig {
//	
//	private final JwtValidationFilter jwtValidationFilter;
//
//	public WebFluxSecurityConfig(JwtValidationFilter jwtValidationFilter) {
//		this.jwtValidationFilter = jwtValidationFilter;
//	}
	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		 http
//	        .cors(cors -> cors.disable())
//	        .csrf(csrf -> csrf.disable())
//	        .sessionManagement(session -> 
//	            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//	        .authorizeHttpRequests(auth -> auth
//	            .requestMatchers("/auth/**").permitAll()
//	            .requestMatchers("/user/**").authenticated()
//	           
//	     
//	        ).addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class);
//		 
//		return http.build();
//		
//	}
	
//	@Bean
//	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//		return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
//				.authorizeExchange(ex -> ex.pathMatchers("/auth/**").permitAll()
//						.pathMatchers("/user/**").authenticated()
//				).addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class).build();
//	}
//	
//	@Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

}
