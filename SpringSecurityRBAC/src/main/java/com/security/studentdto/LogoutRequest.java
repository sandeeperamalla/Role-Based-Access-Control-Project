package com.security.studentdto;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a request to log out a user. This class contains the token that
 * will be used to identify and invalidate the user's session during the logout
 * process.
 */
@Getter
@Setter
public class LogoutRequest {

	/**
	 * The token associated with the user's session. This token will be used to
	 * validate and log out the user.
	 */
	private String token;

}
