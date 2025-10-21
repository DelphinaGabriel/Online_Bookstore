package com.example.userservice.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.userservice.user.dto.UserCredentialDto;
import com.example.userservice.user.dto.UserDto;
import com.example.userservice.user.entity.User;
import com.example.userservice.user.repository.UserRepository;
import com.example.userservice.user.security.JwtUtility;
import com.example.userservice.user.security.UserDetailsServiceImplementation;
import com.example.userservice.user.service.UserService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	private final UserService userService;
	private final UserDetailsServiceImplementation authService;
	private final JwtUtility jwtUtility;

	public UserController(UserService userService, UserDetailsServiceImplementation authService,
			JwtUtility jwtUtility) {
		this.userService = userService;
		this.authService = authService;
		this.jwtUtility = jwtUtility;
	}
	
	@GetMapping("/msg")
	public String msg() {
		return "Test message in user service";
	}

	@PostMapping("/add")
	public UserDto add(@RequestBody UserDto user) {
		return userService.add(user);
	}

	@GetMapping("/list")
	public List<UserDto> getAll() {
		return userService.getAll();
	}

	@GetMapping("/get")
	public UserDto getById(@RequestParam Long id) {
		return userService.getById(id);
	}

	@DeleteMapping("/delete")
	public String delete(@RequestParam Long id) {
		return userService.delete(id);
	}


}
