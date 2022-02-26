package com.olx.exception;

public class InvalidUserNameORPasswordException  extends RuntimeException{

	private String message;
	
	public InvalidUserNameORPasswordException() {
		this.message="";
	}
	
	public InvalidUserNameORPasswordException(String message) {
		this.message=message;
	}

	@Override
	public String toString() {
		return "Invalid UserName OR Password " + message ;
	}
	
	
}
