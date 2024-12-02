package com.security.exception;

public class InvalidJwtToken extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidJwtToken(String msg) {
		super(msg);
	}

}
