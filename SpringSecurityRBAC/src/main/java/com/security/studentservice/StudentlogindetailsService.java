package com.security.studentservice;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.exception.InvalidJwtToken;
import com.security.exception.JwtTokenExpired;
import com.security.jwtservice.JwtService;
import com.security.studentdao.StudentlogindetailsDao;
import com.security.studentdto.StudentLoginDetails;
import com.security.studentdto.StudentLoginForm;

@Service
public class StudentlogindetailsService {

	@Autowired
	private StudentlogindetailsDao dao; // DAO layer for database interactions

	@Autowired
	private JwtService jwtService; // Service for generating and validating JWT tokens

	@Autowired
	private RedisTemplate<String, String> redisTemplate; // Redis template for managing token blacklisting

	@Autowired
	private AuthenticationManager authenticationManager; // Manages authentication for user login

	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12); // Password encoder

	/**
	 * Saves student login details with the password securely encoded.
	 *
	 * @param details The student login details to be saved.
	 * @return Saved student login details.
	 */
	public StudentLoginDetails saveStudentloginDetailsService(StudentLoginDetails details) {
		// Encode the user's password before saving to the database
		details.setPassword(bCryptPasswordEncoder.encode(details.getPassword()));
		return dao.saveLoginDetailsRepo(details);
	}

	/**
	 * Verifies user credentials and generates a JWT token if authentication
	 * succeeds.
	 *
	 * @param form Contains the userName and password for login.
	 * @return A JWT token if authentication is successful, or "fail" otherwise.
	 */
	public String verifyUser(StudentLoginForm form) {
		// Authenticate user credentials using the authentication manager
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(form.getUserName(), form.getPassword()));

		if (authentication.isAuthenticated()) {
			// Extract the user's role from granted authorities
			String role = authentication.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority) // Get the role																	// as a string
					.findFirst() // Take the first (and only) role
					.orElseThrow(() -> new IllegalArgumentException("No roles found"));

			// Generate and return a JWT token with the UserName and role
			return jwtService.generateToken(form.getUserName(), role);
		}

		// Return failure message if authentication fails
		return "fail";
	}
	// log out

	/**
	 * Logs out a user by blacklisting their JWT token in Redis.
	 *
	 * @param authHeader The JWT token from the user's authorization header.
	 * @return A success or error message based on the operation result.
	 */
	public String logOut(String authHeader) {
		// Access Redis operations for key-value storage
		ValueOperations<String, String> valueOps = redisTemplate.opsForValue();

		// Extract the JWT token from the Authorization header
		String token = authHeader;

		try {
			// Extract the unique identifier (jti) from the token
			String jti = jwtService.extractJtiFromToken(token);

			if (jti == null) {
				throw new InvalidJwtToken("Invalid token."); // Handle invalid tokens
			}

			// Calculate the remaining time until the token's expiration
			long tokenExpiryDuration = jwtService.extractExpiration(token).getTime() - System.currentTimeMillis();

			if (tokenExpiryDuration > 0) {
				// Store the token in Redis with a TTL matching its expiration
				valueOps.set("blacklisted:" + token, jti, tokenExpiryDuration, TimeUnit.MILLISECONDS);
				return "Logout successful.";
			} else {
				throw new JwtTokenExpired("Token has already expired."); // Handle expired tokens
			}

		} catch (JwtTokenExpired e) {
			// Handle tokens that are already expired
			return "Token has expired.";
		} catch (InvalidJwtToken e) {
			// Handle invalid or malformed tokens
			return "Invalid or malformed token.";
		} catch (Exception e) {
			// Catch any unexpected errors
			return e.getMessage();
		}
	}

}
