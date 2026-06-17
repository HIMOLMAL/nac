package com.example.nac.repository;

import com.example.nac.entity.ReportingHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReportingHeaderRepository extends JpaRepository<ReportingHeader, Long> {

    List<ReportingHeader> findByUserUserId(Long userId);
    List<ReportingHeader> findByStatus(String status);
}