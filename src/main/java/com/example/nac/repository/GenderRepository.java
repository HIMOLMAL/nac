package com.example.nac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.nac.entity.Gender;

public interface GenderRepository extends JpaRepository<Gender, Long> {
}