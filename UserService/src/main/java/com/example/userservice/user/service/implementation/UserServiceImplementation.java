package com.example.userservice.user.service.implementation;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.userservice.user.dto.UserDto;
import com.example.userservice.user.entity.User;
import com.example.userservice.user.repository.UserRepository;
import com.example.userservice.user.security.JwtUtility;
import com.example.userservice.user.service.UserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImplementation implements UserService{
	
	private ModelMapper MODEL_MAPPER = new ModelMapper();
	
	private final UserRepository userRepository;
	private final JwtUtility jwtUtility;
	 private final PasswordEncoder passwordEncoder;

	public UserServiceImplementation(UserRepository userRepository, JwtUtility jwtUtility, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.jwtUtility = jwtUtility;
		this.passwordEncoder = passwordEncoder;
	}
	
	public UserDto add(UserDto userDto) {
		
		User existsUser = userRepository.findByUsernameAndIsActive(userDto.getEmail(), true);
		if(existsUser != null) {
			throw new RuntimeException("User already exists");
		}
		
		User user = MODEL_MAPPER.map(userDto, User.class);
		String generatedPassword = jwtUtility.generatePassword();
		String encodedPassword = passwordEncoder.encode(generatedPassword);
		user.setPassword(encodedPassword);
		user = userRepository.save(user);
		user.setPassword(generatedPassword);
		return MODEL_MAPPER.map(user, UserDto.class);
	}
	
	public List<UserDto> getAll() {
		List<User> userList = userRepository.findAll();
		return userList.stream().map(e-> MODEL_MAPPER.map(e, UserDto.class)).toList();
	}
	
	public UserDto getById(Long id) {
		User user = userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User not found"));
		return MODEL_MAPPER.map(user, UserDto.class);
	}
	
	public UserDto getByEmail(String email) {
		User user = userRepository.findByEmailAndIsActive(email, true);
		return MODEL_MAPPER.map(user, UserDto.class);
	}
	
	public String delete(Long id) {
		userRepository.deleteById(id);
		return "User deleted successfully...";
	}


}
