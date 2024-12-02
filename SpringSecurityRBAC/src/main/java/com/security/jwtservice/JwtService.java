package com.security.jwtservice;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	// Secret key used to sign JWT tokens, encoded in Base64 format
	private String secretKey = "";

	// Constructor to initialize and generate a secret key using HMAC SHA-256
	// algorithm
	public JwtService() {
		try {
			// Create a KeyGenerator instance for HMAC SHA-256
			KeyGenerator generator = KeyGenerator.getInstance("HmacSHA256");
			// Generate a secret key
			SecretKey sk = generator.generateKey();
			// Encode the key as a Base64 string for storage and usage
			secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			// Log the exception if the algorithm is not found
			e.printStackTrace();
		}
	}

	public String generateToken(String userName, String role) {
		// Add role to claims
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", role); // Add role as a custom claim
//		 claims.put("jti", UUID.randomUUID().toString()); // Add 'jti' claim

		return Jwts.builder()
				.claims()
				.add(claims)
				.subject(userName) // Set the subject (user name)
				.issuedAt(new Date(System.currentTimeMillis())) // Set the issue date
				.expiration(new Date(System.currentTimeMillis() + 45 * 60 * 1000)) // Set the expiration date (45 minutes from now)

				.and().signWith(getKey()) // Sign the token with the generated key
				.compact(); // Build and return the token

	}

	/**
	 * Retrieves the secret key used for signing JWT tokens.
	 * 
	 * @return The secret key as a SecretKey object.
	 */
	private SecretKey getKey() {
		// Decode the Base64-encoded key and create a SecretKey object
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * Extracts the user Name (subject) from the given token.
	 * 
	 * @param token The JWT token.
	 * @return The extracted user id (subject).
	 */
	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * Extracts the role from the given token.
	 *
	 * @param token The JWT token.
	 * @return The extracted role.
	 */
	public String extractRole(String token) {
		Claims claims = extractAllClaims(token);
		return (String) claims.get("role"); // Extract the role claim
	}

	/**
	 * Extracts a specific claim from the token using a function.
	 *
	 * @param token         The JWT token.
	 * @param claimResolver The function to extract the desired claim.
	 * @param <T>           The type of the claim to extract.
	 * @return The extracted claim.
	 */
	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	/**
	 * Extracts all claims from the given token.
	 *
	 * @param token The JWT token.
	 * @return The extracted claims.
	 */
	private Claims extractAllClaims(String token) {
		// Parse and extract claims using the secret key
		return Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	/**
	 * Validates the given token by checking the user email, token expiration, and
	 * role.
	 *
	 * @param token   The JWT token.
	 * @param details The user details for validation.
	 * @param role    The expected role to validate against.
	 * @return True if the token is valid and has the correct role, false otherwise.
	 */
	public boolean validateTokenWithRole(String token, UserDetails details, String role) {
		final String userEmail = extractUserName(token);
		final String tokenRole = extractRole(token); // Extract the role from the token

		// Check if the user id matches, the token is not expired, and the role is valid
		return (userEmail.equals(details.getUsername()) && !isTokenExpired(token) && tokenRole.equals(role));
	}

	/**
	 * Checks if the token is expired.
	 *
	 * @param token The JWT token.
	 * @return True if the token is expired, false otherwise.
	 */
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/**
	 * Extracts the expiration date from the token.
	 *
	 * @param token The JWT token.
	 * @return The expiration date of the token.
	 */
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	/**
	 * Extracts the 'jti' (JWT ID) claim from the provided JWT token.
	 *
	 * @param token the JWT token from which the 'jti' claim is to be extracted
	 * @return the 'jti' claim as a String, or null if the claim is not present in the token
	 */
	public String extractJtiFromToken(String token) {
	    Claims claims = extractAllClaims(token);
	    return claims.get("sub", String.class); // Extract the 'jti' claim as a String
	}


}
