package com.security.studentcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.studentdto.LogoutRequest;
import com.security.studentdto.Role;
import com.security.studentdto.StudentLoginDetails;
import com.security.studentdto.StudentLoginForm;
import com.security.studentservice.StudentlogindetailsService;

/**
 * This controller handles the registration, login, and logout operations for
 * students. It provides EndPoints to register a new student, verify student
 * login, and log out an existing student.
 */
@RestController
public class StudentLoginOrRegisterController {

	@Autowired
	StudentlogindetailsService service;

	/**
	 * EndPoints to register a new student. If the student doesn't provide a role,
	 * the default role is set to "USER".
	 * 
	 * @param details the details of the student to be registered, provided in the
	 *                request body.
	 * @return a ResponseEntity containing the registered student's details and a
	 *         CREATED status.
	 */
	@PostMapping("/registerStudent")
	public ResponseEntity<StudentLoginDetails> saveStudentloginDetailsController(
			@RequestBody StudentLoginDetails details) {

		// Set the default role to USER if not provided
		if (details.getRole() == null) {
			details.setRole(Role.USER);
		}

		// Save the student details and return a CREATED status with the saved details
		return ResponseEntity.status(HttpStatus.CREATED).body(service.saveStudentloginDetailsService(details));
	}

	/**
	 * EndPoints for student login. It verifies the login details provided by the
	 * student.
	 * 
	 * @param form the login details of the student, including username and
	 *             password.
	 * @return a response indicating whether the login was successful or not.
	 */
	@PostMapping("/loginStudent")
	public String studentLogin(@RequestBody StudentLoginForm form) {
		return service.verifyUser(form);
	}

	/**
	 * EndPoints for logging out a student. It accepts a token in the request body
	 * and invalidates it to log the student out.
	 * 
	 * @param logoutRequest contains the token used for logging out.
	 * @return a ResponseEntity with a message indicating whether the logout was
	 *         successful or failed.
	 */
	@PostMapping("/logoutStudent")
	public ResponseEntity<String> logout(@RequestBody LogoutRequest logoutRequest) {
		String token = logoutRequest.getToken();

		// Check if token is provided
		if (token == null || token.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No token provided.");
		}

		// Attempt to log the user out using the provided token
		String result = service.logOut(token);

		// Return appropriate status based on the result of logout
		if ("Logout successful.".equals(result)) {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
		}
	}
}
