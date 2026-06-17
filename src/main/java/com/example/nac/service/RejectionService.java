package com.example.nac.service;

import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.stereotype.Service;
import com.example.nac.entity.ReportingHeader;
import com.example.nac.repository.ReportingHeaderRepository;

@Service
public class RejectionService {

    private final ReportingHeaderRepository headerRepository;

    
    public RejectionService(ReportingHeaderRepository headerRepository) {
        this.headerRepository = headerRepository;
    }

    public void reject(Long id, String comment) {
        Objects.requireNonNull(id, "Report id must not be null");
        ReportingHeader report = headerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Report not found: " + id));

        report.setStatus("REJECTED");
        report.setRejectionReason(comment);
        report.setReviewedBy("Manager");
        report.setReviewedAt(LocalDateTime.now());

        headerRepository.save(report);
    }

}