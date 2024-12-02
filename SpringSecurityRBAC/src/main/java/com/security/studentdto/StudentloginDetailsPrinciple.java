package com.security.studentdto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Custom implementation of the UserDetails interface for Spring Security. This
 * class holds the student's login details and their role, and provides the
 * necessary methods for Spring Security to authenticate and authorize users. It
 * maps the student's role to a granted authority for access control.
 */
public class StudentloginDetailsPrinciple implements UserDetails {

	/**
	 * Serial version UID for serialization. Ensures that the class's serialized
	 * version is compatible with the JVM.
	 */
	private static final long serialVersionUID = 1247967939454057768L;

	// The student's login details (UserName, password, etc.)
	private StudentLoginDetails details;

	// The role of the student (e.g., USER, ADMIN)
	private Role role;

	/**
	 * Constructor to initialize the StudentloginDetailsPrinciple object.
	 * 
	 * @param details The student's login details (UserName, password, etc.)
	 * @param role    The role of the student (USER, ADMIN, etc.)
	 */
	public StudentloginDetailsPrinciple(StudentLoginDetails details, Role role) {
		this.details = details;
		this.role = role;
	}

	/**
	 * Returns the authorities granted to the student based on their role. The role
	 * is prefixed with "ROLE_" to match the expected format for Spring Security.
	 * 
	 * @return A collection of granted authorities based on the student's role.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
	}

	/**
	 * Returns the student's password for authentication. This method is used by
	 * Spring Security to verify the student's credentials.
	 * 
	 * @return The password of the student.
	 */
	@Override
	public String getPassword() {
		return details.getPassword();
	}

	/**
	 * Returns the student's UserName for authentication. This method is used by
	 * Spring Security to identify the student.
	 * 
	 * @return The UserName of the student.
	 */
	@Override
	public String getUsername() {
		return details.getUserName();
	}
}
