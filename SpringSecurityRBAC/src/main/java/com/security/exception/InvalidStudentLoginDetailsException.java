package com.security.exception;

public class InvalidStudentLoginDetailsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7355292923587622791L;

	public InvalidStudentLoginDetailsException(String msg) {
		super(msg);
	}

}
