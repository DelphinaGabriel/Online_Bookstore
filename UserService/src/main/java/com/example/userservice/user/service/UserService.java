package com.example.userservice.user.service;

import java.util.List;

import com.example.userservice.user.dto.UserDto;
import com.example.userservice.user.entity.User;

public interface UserService {

	UserDto add(UserDto user);
	List<UserDto> getAll();
	UserDto getById(Long id);
	UserDto getByEmail(String email);
	String delete(Long id);
}
