package com.example.nac.controller;

import org.springframework.web.bind.annotation.*;

import com.example.nac.repository.UserRepository;
import com.example.nac.repository.PasswordResetTokenRepository;
import com.example.nac.entity.User;
import com.example.nac.entity.PasswordResetToken;



import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;
import java.util.UUID;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/password")
public class ForgotPasswordController {

    private final UserRepository userRepo;
    private final PasswordResetTokenRepository tokenRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public ForgotPasswordController(UserRepository userRepo,
                                    PasswordResetTokenRepository tokenRepo,
                                    BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/forgot")
    public String forgotPassword(@RequestBody Map<String, String> request) {

        String email = request.get("email");

        User user = userRepo.findByEmail(email).orElse(null);

        if (user == null) {
            return "User not found";
        }

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));

        tokenRepo.save(resetToken);

        String link = "http://localhost:8080/reset-password.html?token=" + token;

        System.out.println("Reset link: " + link);

        return "Reset link sent (check console)";
    }

    @PostMapping("/reset")
    public String resetPassword(@RequestBody Map<String, String> request) {

        String token = request.get("token");
        String newPassword = request.get("newPassword");

        PasswordResetToken resetToken = tokenRepo.findByToken(token);

        if (resetToken == null) {
            return "Invalid token";
        }

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return "Token expired";
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));

        userRepo.save(user);

        return "Password reset successful!";
    }
}