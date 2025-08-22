package com.laitusneo.backend.service;

import com.laitusneo.backend.entity.User;
import com.laitusneo.backend.repository.UserRepository;
import com.laitusneo.backend.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
        // Register a new user
        public String register(com.laitusneo.backend.dto.RegisterRequest request) {
            com.laitusneo.backend.entity.User.RoleType roleType = com.laitusneo.backend.entity.User.RoleType.EMPLOYEE;
            if (request.getRole() != null) {
                try {
                    roleType = com.laitusneo.backend.entity.User.RoleType.valueOf(request.getRole().toUpperCase());
                } catch (IllegalArgumentException e) {
                    // fallback to EMPLOYEE if invalid role
                }
            }
            String hashedPassword = passwordEncoder.encode(request.getPassword());
            com.laitusneo.backend.entity.User user = com.laitusneo.backend.entity.User.builder()
                    .username(request.getUsername())
                    .password(hashedPassword)
                    .email(request.getEmail())
                    .role(roleType)
                    .isActive(true)
                    .createdAt(java.time.LocalDateTime.now())
                    .updatedAt(java.time.LocalDateTime.now())
                    .build();
            userRepository.save(user);
            return "User registered successfully";
        }

        public com.laitusneo.backend.dto.AuthResponse login(com.laitusneo.backend.dto.LoginRequest request) {
            com.laitusneo.backend.entity.User user = userRepository.findByUsername(request.getUsernameOrEmail()).orElse(null);
            if (user == null) {
                user = userRepository.findByEmail(request.getUsernameOrEmail()).orElse(null);
            }
            if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
                System.out.println("Generated JWT Token: " + token); // Log the token for debugging
                return new com.laitusneo.backend.dto.AuthResponse(
                    token,
                    user.getUsername(),
                    user.getRole().name()
                );
            }
            System.out.println("Login failed: Invalid credentials or user not found.");
            return null;
        }
}
