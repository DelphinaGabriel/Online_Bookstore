package com.example.userservice.user.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class CustomAuthenticationDetails {

	private WebAuthenticationDetails webAuthenticationDetails;
	private List<SimpleGrantedAuthority> roles;

	public WebAuthenticationDetails getWebAuthenticationDetails() {
		return webAuthenticationDetails;
	}

	public void setWebAuthenticationDetails(WebAuthenticationDetails webAuthenticationDetails) {
		this.webAuthenticationDetails = webAuthenticationDetails;
	}

	public List<SimpleGrantedAuthority> getRoles() {
		return roles;
	}

	public void setRoles(List<SimpleGrantedAuthority> roles) {
		this.roles = roles;
	}

	public CustomAuthenticationDetails(WebAuthenticationDetails webAuthenticationDetails,
			List<SimpleGrantedAuthority> roles) {
		super();
		this.webAuthenticationDetails = webAuthenticationDetails;
		this.roles = roles;
	}

}
