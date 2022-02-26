package com.olx.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
public class GlobalHandlerException extends ResponseEntityExceptionHandler  {

	@ExceptionHandler(value = {InvalidUserNameORPasswordException.class,InvalidAuthTokenException.class})
	public ResponseEntity<Object> handleException(RuntimeException  exception, WebRequest request) {
		return handleExceptionInternal(exception, "{\"error\": \"" + exception.toString() +
				"\"}", new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
			}
	

	
	
}
