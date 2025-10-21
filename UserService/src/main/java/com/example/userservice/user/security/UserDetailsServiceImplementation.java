package com.example.userservice.user.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.userservice.user.dto.UserCredentialDto;
import com.example.userservice.user.entity.Role;
import com.example.userservice.user.entity.User;
import com.example.userservice.user.repository.UserRepository;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	public UserDetailsServiceImplementation(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameAndIsActive(username, true);
		System.err.println("database user = "+user);
		System.err.println("user username = "+ username);
		System.err.println("user password = "+ user.getPassword());
		if(user == null){
            throw new UsernameNotFoundException("User not found");
        }

		Set<GrantedAuthority> grantedAuthorityList = new HashSet();
		for(Role role : user.getUserRoles()) {
			grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_"+role.getName().toUpperCase()));
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorityList);
	}
	
}
