package com.example.nac.repository;



import com.example.nac.entity.Indicator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndicatorRepository extends JpaRepository<Indicator, Long> {
}