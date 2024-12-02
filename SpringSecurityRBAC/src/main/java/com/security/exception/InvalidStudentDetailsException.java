package com.security.exception;

public class InvalidStudentDetailsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3954705943407339837L;

	public InvalidStudentDetailsException(String message) {
		super(message);
	}
}
