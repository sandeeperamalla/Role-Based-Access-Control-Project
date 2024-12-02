package com.security.studentdto;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the form data required for student login. This class contains the
 * student's userName and password, which are used for authentication during the
 * login process.
 */
@Setter
@Getter
public class StudentLoginForm {

	/**
	 * The userName of the student attempting to log in. This will be used to
	 * identify the student in the system.
	 */
	private String userName;

	/**
	 * The password associated with the student's account. This will be used to
	 * authenticate the student's identity.
	 */
	private String password;

}
