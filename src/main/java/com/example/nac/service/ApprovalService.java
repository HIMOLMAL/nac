package com.example.nac.service;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.stereotype.Service;
import com.example.nac.entity.ReportingHeader;
import com.example.nac.repository.ReportingHeaderRepository;

@Service
public class ApprovalService {

    private final ReportingHeaderRepository headerRepository;

   
    public ApprovalService(ReportingHeaderRepository headerRepository) {
        this.headerRepository = headerRepository;
    }

    public void approve(Long id, String comment) {
        Objects.requireNonNull(id, "id must not be null");
        ReportingHeader report = headerRepository.findById(id).orElseThrow();
        report.setStatus("APPROVED");
        report.setApprovalComment(comment);
        report.setReviewedBy("Manager");
        report.setReviewedAt(LocalDateTime.now());
        headerRepository.save(report);
    }
}
  