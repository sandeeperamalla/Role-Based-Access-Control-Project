package com.security.jwtfilter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // Marks this class as a Spring-managed component
public class jwtBlockListFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate<String, String> redisTemplate; // Used to interact with Redis for token blacklisting

    /**
     * This method is executed once per request. It checks if the JWT token in the 
     * request is blacklisted. If blacklisted, the request is rejected.
     *
     * @param request     The incoming HTTP request.
     * @param response    The outgoing HTTP response.
     * @param filterChain The filter chain to pass the request to the next filter.
     * @throws ServletException If an error occurs in filtering.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extract the Authorization header from the incoming request
        String authHeader = request.getHeader("Authorization");

        // Check if the Authorization header is present and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extract the token by removing the "Bearer " prefix
            String token = authHeader.substring(7);

            // Check in Redis if the token is blacklisted
            if (redisTemplate.hasKey("blacklisted:" + token)) {
                // If blacklisted, set the response as Forbidden (403) with a custom error message
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("{\"error\": \"Forbidden\", \"message\": \"Token has expired. Please log in again\"}");
                return; // Stop further processing of the request
            }
        }

        // If the token is not blacklisted, pass the request to the next filter in the chain
        filterChain.doFilter(request, response);
    }
}
