package com.cts.backend.elibrary.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends RuntimeException {

	private HttpStatus status;
	private String message;
	public InvalidTokenException(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	
	
}
