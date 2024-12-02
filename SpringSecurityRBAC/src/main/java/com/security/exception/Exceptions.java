package com.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class Exceptions {

	/**
	 * Handles the exception when student details are not found with the given ID.
	 * 
	 * @param detailsNotFound the exception object containing details about the
	 *                        error
	 * @return a ResponseEntity with HTTP status NOT_FOUND and the exception message
	 *         as the response body
	 */
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(StudentDetailsNotFoundWithId.class)
	public ResponseEntity<Object> StudentDetailsNotFoundException(StudentDetailsNotFoundWithId detailsNotFound) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detailsNotFound.getMessage());
	}

	/**
	 * Handles the exception when invalid student details are provided.
	 * 
	 * @param detailsNotFound the exception object containing details about the
	 *                        error
	 * @return a ResponseEntity with HTTP status BAD_REQUEST and the exception
	 *         message as the response body
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidStudentDetailsException.class)
	public ResponseEntity<Object> invalidStudentDetalis(InvalidStudentDetailsException detailsNotFound) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detailsNotFound.getMessage());
	}

	/**
	 * Handles the exception when there is an error during student details creation.
	 * 
	 * @param detailsNotFound the exception object containing details about the
	 *                        error
	 * @return a ResponseEntity with HTTP status BAD_REQUEST and the exception
	 *         message as the response body
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(StudentDetailsCreationException.class)
	public ResponseEntity<Object> studentDetailsCreationException(StudentDetailsCreationException detailsNotFound) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detailsNotFound.getMessage());
	}

	/**
	 * Handles the exception when an invalid EndPoint is accessed.
	 * 
	 * @param foundException the exception object containing details about the error
	 * @return a ResponseEntity with HTTP status NOT_FOUND and a custom message as
	 *         the response body
	 */
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<Object> inValidEndPoints(NoResourceFoundException foundException) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EndPoints is Invalid");
	}

	/**
	 * Handles the exception when an HTTP method is not allowed for a specific
	 * EndPoint.
	 * 
	 * @param supportedException the exception object containing details about the
	 *                           error
	 * @return a ResponseEntity with HTTP status METHOD_NOT_ALLOWED and the
	 *         exception message as the response body
	 */
	@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Object> httpMethodInValid(HttpRequestMethodNotSupportedException supportedException) {
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(supportedException.getMessage());
	}

	/**
	 * Handles the exception when invalid student login details are provided.
	 * 
	 * @param detailsNotFound the exception object containing details about the
	 *                        error
	 * @return a ResponseEntity with HTTP status BAD_REQUEST and the exception
	 *         message as the response body
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidStudentLoginDetailsException.class)
	public ResponseEntity<Object> invalidStudentLoginDetalis(InvalidStudentLoginDetailsException detailsNotFound) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detailsNotFound.getMessage());
	}

	/**
	 * Handles the exception when there is an error during student login details
	 * creation.
	 * 
	 * @param detailsNotFound the exception object containing details about the
	 *                        error
	 * @return a ResponseEntity with HTTP status BAD_REQUEST and the exception
	 *         message as the response body
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(StudentLoginDetailsCreationException.class)
	public ResponseEntity<Object> studentLoginDetailsCreationException(
			StudentLoginDetailsCreationException detailsNotFound) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detailsNotFound.getMessage());
	}

	/**
	 * Handles the exception when an invalid JWT token is provided.
	 * 
	 * @param detailsNotFound the exception object containing details about the
	 *                        error
	 * @return a ResponseEntity with HTTP status BAD_REQUEST and the exception
	 *         message as the response body
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidJwtToken.class)
	public ResponseEntity<Object> InvalidJwtTokenException(InvalidJwtToken detailsNotFound) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detailsNotFound.getMessage());
	}

	/**
	 * Handles the exception when a JWT token has expired.
	 * 
	 * @param expired the exception object containing details about the error
	 * @return a ResponseEntity with HTTP status BAD_REQUEST and the exception
	 *         message as the response body
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(JwtTokenExpired.class)
	public ResponseEntity<Object> JwtTokenExpiredException(JwtTokenExpired expired) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(expired.getMessage());
	}

}
