package com.example.userservice.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.userservice.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsernameAndIsActive(String username, Boolean isActive);

	User findByEmailAndIsActive(String email, boolean b);
	
}
