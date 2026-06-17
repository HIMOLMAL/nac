package com.example.nac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.nac.entity.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);
}




