package com.olx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "USER DTO")
public class User {
	@ApiModelProperty(value = "User Identification")
	private int id;
	@ApiModelProperty(value = "User First Name")
	private String firstName;
	@ApiModelProperty(value = "User Last Name")
	private String lastName;
	@ApiModelProperty(value = "User Name")
	private String username;
	@ApiModelProperty(value = "User Password")
	private String password;
	@ApiModelProperty(value = "User Email")
	private String email;
	@ApiModelProperty(value = "User Phone Number")
	private long phoneNumber;
	@ApiModelProperty(value = "User Roles")
	private String roles;
	//@ApiModelProperty(value = "User active")
	private boolean active;

	
	public User(){}

	public User(String firstName, String lastName, String username, String password, String email, long phoneNumber,String roles,boolean active) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.roles=roles;
		this.active=active;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		User user = (User) obj;
		if (this.firstName == null) {
			return false;
		}
		if (this.firstName.equals(user.firstName)) {
			return true;
		}
		return false;
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", password=" + password + ", email=" + email + ", phoneNumber=" + phoneNumber + ", roles=" + roles
				+ ", active=" + active + "]";
	}

	
	
	
	
}
