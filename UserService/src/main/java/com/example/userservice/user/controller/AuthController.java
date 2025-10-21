package com.example.userservice.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.userservice.user.dto.UserCredentialDto;
import com.example.userservice.user.dto.UserDto;
import com.example.userservice.user.security.JwtUtility;
import com.example.userservice.user.security.UserDetailsServiceImplementation;
import com.example.userservice.user.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;
	private final UserDetailsServiceImplementation authService;
	private final JwtUtility jwtUtility;

	public AuthController(UserService userService, UserDetailsServiceImplementation authService,
			JwtUtility jwtUtility, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.authService = authService;
		this.jwtUtility = jwtUtility;
		this.passwordEncoder = passwordEncoder;
	}
	
	@GetMapping("/msg")
	public String msg() {
		return "Test message in auth service";
	}

	@PostMapping("/login")
	public UserDto login(@RequestBody UserCredentialDto userCredentialDto) {
		System.err.println("enter user login");
		try {
			UserDetails userDetails = authService.loadUserByUsername(userCredentialDto.getUsername());
			System.err.println("user credential password = "+userCredentialDto.getPassword());
			System.err.println("user details password = "+userDetails.getPassword());
			if (!passwordEncoder.matches(userCredentialDto.getPassword(), userDetails.getPassword())) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username and password");
			}

			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
					userCredentialDto.getUsername(), null, userDetails.getAuthorities()));
			String token = jwtUtility.generateJwtTokenWithSecretKey(userDetails);
			UserDto userDto = userService.getByEmail(userCredentialDto.getUsername());
			userDto.setToken(token);
			return userDto;
		} catch (UsernameNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
		} catch (ResponseStatusException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Authentication failed");
		}

	}

}
