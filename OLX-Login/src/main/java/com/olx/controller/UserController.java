package com.olx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olx.dto.AuthenticateRequest;
import com.olx.dto.User;
import com.olx.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/olx-login")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping(value = "/user/authenticate", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	 @ApiOperation(value = "Authentication of user ",notes = "Return authentication user")
	public ResponseEntity<String> authenticate(@RequestBody AuthenticateRequest authenticateRequest) {
		return new ResponseEntity<String>(userService.authenticate(authenticateRequest), HttpStatus.OK);

	}

	@GetMapping(value = "/user/token/validate")
	@ApiOperation(value = "validate the User token", notes = "check the validate the User token")
	public ResponseEntity<Boolean> validateUserToken(@RequestHeader("Authorization") String authToken) {
		return new ResponseEntity<Boolean>(userService.validateUserToken(authToken), HttpStatus.OK);
	}

	@DeleteMapping(value = "/user/logout")
    @ApiOperation(value = "Authentication of user is logout ",notes = "Return logout authentication user")
	public ResponseEntity<Boolean> userLogout(@RequestHeader("Authorization") String authToken) {
		return new ResponseEntity<Boolean>(userService.userLogout(authToken), HttpStatus.OK);

	}

	@PostMapping(value = "/user", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "register the user for OLX LOGIN",notes = "added the user for OLX LOGIN")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);

	}

	@GetMapping(value = "/user", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Read the User for OLXLogin purpose", notes = "Return the authenticate user")
	public ResponseEntity<User> getUser( @RequestHeader("Authorization") String authToken ) {
		return new ResponseEntity<User>(userService.getUser(authToken), HttpStatus.OK);

	}

}
