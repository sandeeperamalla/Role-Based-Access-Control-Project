package com.security.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.security.jwtfilter.JwtFilter;
import com.security.jwtfilter.jwtBlockListFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JwtFilter jwtFilter; // Custom JWT filter for validating JWT tokens
    @Autowired
    jwtBlockListFilter jwtBlockListFilter; // Filter for handling blocked JWT tokens
    
    
    /** 
     * NOTE:
     * User Can access only the EndPoints intended for the User role (/user/**).
     * 
     * Moderator Has access to both User and Moderator EndPoints (/user/** and /moderator/**).
     * This includes all permissions granted to User plus additional permissions for Moderator	
     * 
     * Admin Has access to all EndPoints, including Admin, Moderator, and User (/admin/**, /moderator/**, and /user/**).
     * This encompasses all permissions granted to both User and Moderator roles.
     * 
     * */
    
    

    /**
     * Configures the security settings for the application.
     * 
     * @param httpSecurity The HttpSecurity object to configure security features.
     * @return A configured SecurityFilterChain.
     * @throws Exception If any error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securitySettings(HttpSecurity httpSecurity) throws Exception {
        // Disable CSRF protection (not recommended for non-stateless applications)
        httpSecurity.csrf(csrf -> csrf.disable());

        // Define authorization rules for different request paths
        httpSecurity.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/registerStudent", "/loginStudent").permitAll() // Allow public access
                .requestMatchers("/logoutStudent").hasAnyRole("USER", "MODERATOR", "ADMIN") // Role-based access
                .requestMatchers("/admin/**").hasRole("ADMIN") // Admin-only access
                .requestMatchers("/moderator/**").hasAnyRole("MODERATOR", "ADMIN") // Moderator and Admin access
                .requestMatchers("/user/**").hasAnyRole("USER", "MODERATOR", "ADMIN") // User, Moderator, and Admin access
                .anyRequest().authenticated() // All other requests require authentication
        );

        // Handle authentication failures with a custom entry point
        httpSecurity.exceptionHandling(exceptionHandling -> 
            exceptionHandling.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        );

        // Use basic authentication for additional security layers
        httpSecurity.httpBasic(Customizer.withDefaults());

        // Configure STATELESS session management for JWT-based authentication
        httpSecurity.sessionManagement(session -> 
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // Add custom filters before the default UsernamePasswordAuthenticationFilter
        httpSecurity.addFilterBefore(jwtBlockListFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    /**
     * Configures the authentication provider with user details service and password encoder.
     * 
     * @param detailsService The UserDetailsService for user authentication.
     * @return A configured AuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService detailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12)); // Use BCrypt for password encoding
        provider.setUserDetailsService(detailsService); // Set custom user details service
        return provider;
    }

    /**
     * Provides the authentication manager for the application.
     * 
     * @param authenticationConfiguration The configuration for authentication.
     * @return A configured AuthenticationManager.
     * @throws Exception If any error occurs during configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

