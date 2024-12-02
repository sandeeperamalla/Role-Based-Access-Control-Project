package com.security.jwtfilter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.jwtservice.JwtService;
import com.security.studentservice.StudentDetailsLoginSecurityDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

/**
 * JwtFilter is a custom filter that extends OncePerRequestFilter to ensure it
 * is executed once per request. It is used to handle JWT-based authentication
 * by validating the token and setting the authentication details in the
 * SecurityContext if the token is valid.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

	// Autowires the JwtService, which provides methods to extract user details from
	// the token
	@Autowired
	JwtService jwtService;

	// Autowires the application context to retrieve beans, such as the
	// LoginUserDetails service
	@Autowired
	ApplicationContext applicationContext;

	/**
	 * The doFilterInternal method is overridden to provide the JWT token handling
	 * logic. It is called once per request, checking for the Authorization header,
	 * validating the token, and setting the security context.
	 */

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Retrieve the Authorization header from the request
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String userName = null;

		// Check if the Authorization header is not null and starts with "Bearer "
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			// Extract the token from the header (ignoring "Bearer ")
			token = authHeader.substring(7);
			// Use JwtService to extract the username (or id) from the token
			userName = jwtService.extractUserName(token);
		}

		// Proceed if userId is present and authentication is not yet set
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// Load user details from the UserDetails service using the extracted username
			UserDetails details = applicationContext.getBean(StudentDetailsLoginSecurityDetails.class)
					.loadUserByUsername(userName);

			// Extract the role from the details object
			String userRole = details.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst()
					.orElse(null); // Get the first role (if multiple roles exist, you can modify this)

			// Validate the token with the role from UserDetails
			if (jwtService.validateTokenWithRole(token, details, userRole)) {
				// Create an authentication token for the user
				UsernamePasswordAuthenticationToken passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						details, null, details.getAuthorities());
				// Set details for the authentication token, including request info
				passwordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// Set the authentication in the SecurityContext
				SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
			}

		}

		// Continue the filter chain for further processing
		filterChain.doFilter(request, response);

	}
}