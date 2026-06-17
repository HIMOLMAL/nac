package com.example.nac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.nac.entity.PopulationType;

public interface PopulationTypeRepository extends JpaRepository<PopulationType, Long> {
}