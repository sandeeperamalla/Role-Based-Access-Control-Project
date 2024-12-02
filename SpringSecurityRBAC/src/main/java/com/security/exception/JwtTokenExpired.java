package com.security.exception;

public class JwtTokenExpired extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JwtTokenExpired(String msg) {
		super(msg);
	}

}
