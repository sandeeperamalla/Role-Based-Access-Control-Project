package com.security.studentservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.studentdto.StudentLoginDetails;
import com.security.studentdto.StudentloginDetailsPrinciple;
import com.security.studentrepo.StudentloginDetailsRepo;

@Service
public class StudentDetailsLoginSecurityDetails implements UserDetailsService {

	@Autowired
	private StudentloginDetailsRepo detailsRepo; // Repository for accessing student login details

	/**
	 * Loads the user details by UserName for authentication.
	 *
	 * @param UserName The UserName of the user to load.
	 * @return UserDetails object containing user information for Spring Security.
	 * @throws UsernameNotFoundException If the user is not found in the database.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Fetch student login details from the repository
		StudentLoginDetails details = detailsRepo.findByUserName(username);

		// If no details found, throw exception
		if (details == null) {
			throw new UsernameNotFoundException("Student login details not found for username: " + username);
		}

		// Return a UserDetails implementation with the loaded details
		return new StudentloginDetailsPrinciple(details, details.getRole());
	}
}
