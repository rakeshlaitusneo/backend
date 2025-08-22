package com.laitusneo.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
                        
                        // Swagger UI and OpenAPI endpoints
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-resources/**").permitAll()
                        .requestMatchers("/webjars/**").permitAll()

                        // Department & Role Management (Admin only)
                        .requestMatchers("/api/departments/**", "/api/roles/**").hasRole("ADMIN")

                        // Employee Management (Admin, Manager)
                        .requestMatchers("/api/employees/**").hasAnyRole("ADMIN", "MANAGER")

                        // Attendance (Employee can punch, Manager/Admin can view)
                        .requestMatchers("/api/attendance/**").hasAnyRole("ADMIN", "MANAGER", "EMPLOYEE")

                        // Leave Requests (Employee apply, Manager/Admin approve/reject)
                        .requestMatchers("/api/leave-requests/**").hasAnyRole("ADMIN", "MANAGER", "EMPLOYEE")

                        // Leave Types (Admin only)
                        .requestMatchers("/api/leave-types/**").hasRole("ADMIN")

                        // Salaries (Admin only)
                        .requestMatchers("/api/salaries/**").hasRole("ADMIN")

                        // Email Notifications (Admin only)
                        .requestMatchers("/api/email-notifications/**").hasRole("ADMIN")

                        // Any other request must be authenticated
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}