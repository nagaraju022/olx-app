package com.olx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( value = "AuthenticateRequest DTO")
public class AuthenticateRequest {
	
	@ApiModelProperty(value = "User Name")
	private String username;
	@ApiModelProperty(value = "User Password")
	private String password;
	
	public AuthenticateRequest() {
	}

	public AuthenticateRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Override
	public boolean equals(Object obj) {
		AuthenticateRequest authenticate = (AuthenticateRequest) obj;
		if (this.username == null) {
			return false;
		}
		if (this.username.equals(authenticate.username)) {
			return true;
		}
		return false;
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "AuthenticateRequest [username=" + username + ", password=" + password + "]";
	}
	
	
	

}
