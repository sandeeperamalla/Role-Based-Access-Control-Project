package com.security.studentdto;

/**
 * enum representing the different roles a user can have in the system. The
 * roles are used to manage permissions and access levels for different users.
 * 
 * The roles included are: - USER: Regular user with basic access. - ADMIN:
 * Administrator with full access and management privileges. - MODERATOR: User
 * with intermediate access, typically for content moderation.
 */
public enum Role {
	USER, // Regular user role with basic access.
	ADMIN, // Admin role with full access and administrative privileges.
	MODERATOR // Moderator role with special permissions for overseeing content.
}
