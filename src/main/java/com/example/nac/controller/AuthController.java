package com.example.nac.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nac.dto.LoginRequest;
import com.example.nac.dto.RegisterRequest;
import com.example.nac.entity.Role;
import com.example.nac.entity.User;
import com.example.nac.repository.RoleRepository;
import com.example.nac.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepo;

    private final RoleRepository roleRepo;

    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // ========================= REGISTER =========================
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        try {
            if (userRepo.findByUsername(request.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
            }

            // Fetch Role entity from DB (not a String)
            Role role = roleRepo.findByRoleName(request.getRoleName())
                    .orElseThrow(() -> new RuntimeException("Role not found: " + request.getRoleName()));

            // Create user
            User user = new User();
            user.setFullName(request.getFullName());
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(role);  // ✅ assign Role object

            userRepo.save(user);

            // Build response
            Map<String, Object> response = new HashMap<>();
            response.put("userId", user.getUserId());
            response.put("username", user.getUsername());
            response.put("role", user.getRole().getRoleName()); // ✅ get role name
            response.put("message", "Registration successful");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    // ========================= LOGIN =========================
    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {

    Map<String, Object> response = new HashMap<>();

    Optional<User> userOpt = userRepo.findByUsername(request.getUsername());

    if (userOpt.isPresent()) {

        User user = userOpt.get();

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            // 🔥 STANDARDIZED RESPONSE (VERY IMPORTANT)
            response.put("userId", user.getUserId());
            response.put("username", user.getUsername());
            response.put("fullName", user.getFullName());
            response.put("role", user.getRole().getRoleName());
            response.put("message", "Login successful");

            return ResponseEntity.ok(response);
        }
    }

    response.put("error", "Invalid username or password");
    return ResponseEntity.status(401).body(response);
}
}
