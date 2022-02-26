package com.olx.exception;

public class InvalidAuthTokenException  extends RuntimeException{
	
	String message;

	public InvalidAuthTokenException() {
		this.message="";
	}

	public InvalidAuthTokenException(String message) {
     this.message=message;
	}

	@Override
	public String toString() {
		return "InvalidAuthToken" + message ;
	}
	
	

}
