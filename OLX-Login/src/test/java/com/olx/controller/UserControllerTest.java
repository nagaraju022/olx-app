package com.olx.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olx.dto.AuthenticateRequest;
import com.olx.dto.User;
import com.olx.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	UserService userService;

	@MockBean
	UserDetailsService userDetailsService;

	User actualuser = null;
	
	

	@Test
	public void testAuthenticate_success() throws Exception {

		AuthenticateRequest authRequest = new AuthenticateRequest();
		authRequest.setUsername("nagaraju");

		String token = "NS345";

		when(this.userService.authenticate(authRequest)).thenReturn(token);

		mockMvc.perform(post("/olx-login/user/authenticate").contentType("application/json")
				.content(objectMapper.writeValueAsString(authRequest))

		).andExpect(status().isOk()).andExpect(content().string(containsString("NS3"))).andReturn();

	}

	@Test
	public void testCreateUser_success() throws Exception {

		actualuser = new User();
		actualuser.setFirstName("john");
		// actualuser.setPhoneNumber(123566);

		User expectuser = new User();
		expectuser.setFirstName("john");
		// expectuser.setPhoneNumber(123566);

		when(this.userService.createUser(actualuser)).thenReturn(checkExpectedOrNot(expectuser));

		mockMvc.perform(post("/olx-login/user").contentType("application/json")
				.content(objectMapper.writeValueAsString(actualuser))).andExpect(status().isCreated())
				.andExpect(content().string(containsString("john"))).andReturn();

	}

	private User checkExpectedOrNot(User expectuser) {
		if (expectuser.getFirstName().equals(actualuser.getFirstName())) {
			return expectuser;
		}
		return null;
	}
	

	@Test
	public void testGetUser_success() throws Exception{
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "BS123");

		
		User expUser=new User();
		expUser.setFirstName("nagaraju");
		
		
		when(this.userService.getUser("BS123")).thenReturn(expUser);
		
		mockMvc.perform(get("/olx-login/user")
				.accept("application/json")
				.headers(headers)
				)
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("nagaraju")))
		.andReturn();

		
		
		
	}
	
	@Test
	public void testValidateUserToken_success() throws Exception{
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "NK8822");

		String token="NK8822";
		
		when(this.userService.validateUserToken(token)).thenReturn(true);
		
		mockMvc.perform(get("/olx-login/user/token/validate")
				.accept("application/json")
				.headers(headers)
				)
		.andExpect(status().isOk())
		.andReturn();
		
	}
	
	
	@Test
	public void testUserLogout_success() throws Exception{
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "NK8822");
		
		String token="NK8822";
		
		when(this.userService.userLogout(token)).thenReturn(true);
		
		mockMvc.perform(delete("/olx-login/user/logout")
				.accept("application/json")
				.headers(headers)
				)
		.andExpect(status().isOk())
		.andReturn();
		
		
		
		
	}

}

