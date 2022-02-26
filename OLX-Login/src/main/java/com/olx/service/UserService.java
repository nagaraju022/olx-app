package com.olx.service;

import com.olx.dto.AuthenticateRequest;
import com.olx.dto.User;

public interface UserService {

	String authenticate(AuthenticateRequest authenticateRequest);
	boolean userLogout(String authToken);
	User createUser(User user);
	User getUser(String authToken);
	boolean validateUserToken(String authToken);

}
