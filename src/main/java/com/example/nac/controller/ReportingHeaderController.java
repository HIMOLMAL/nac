package com.example.nac.controller;

import com.example.nac.entity.ReportingHeader;
import com.example.nac.entity.User;
import com.example.nac.repository.ReportingHeaderRepository;
import com.example.nac.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reporting-header")
@CrossOrigin(origins = "*")
public class ReportingHeaderController {

    private final ReportingHeaderRepository reportingHeaderRepository;
    private final UserRepository userRepository;

    public ReportingHeaderController(ReportingHeaderRepository reportingHeaderRepository,
                                     UserRepository userRepository) {
        this.reportingHeaderRepository = reportingHeaderRepository;
        this.userRepository = userRepository;
    }

    // =========================
    // CREATE REPORT
    // =========================
    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createReportingHeader(
            @PathVariable long userId,
            @RequestBody ReportingHeader header) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        header.setUser(user);

        // Workflow defaults
        header.setStatus("PENDING");
        header.setSubmittedAt(LocalDateTime.now());

        ReportingHeader saved =
                reportingHeaderRepository.save(header);

        return ResponseEntity.ok(saved);
    }

    // =========================
    // GET ALL REPORTS
    // =========================
    @GetMapping
    public List<ReportingHeader> getAllHeaders() {
        return reportingHeaderRepository.findAll();
    }
@GetMapping("/pending")
public List<ReportingHeader> getPending() {
    return reportingHeaderRepository.findByStatus("PENDING");
}

@GetMapping("/{id:[0-9]+}")
public ResponseEntity<?> getById(@PathVariable long id) {

     ReportingHeader report = reportingHeaderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found: " + id));
    return ResponseEntity.ok(report);
}
    
    // =========================
    // GET REPORTS BY USER
    // =========================
    @GetMapping("/user/{userId}")
    public List<ReportingHeader> getByUser(
            @PathVariable long userId) {

        return reportingHeaderRepository
                .findByUserUserId(userId);
    }

    // =========================
    // GET REPORT BY ID
    // =========================

       // =========================
    // APPROVE REPORT
    // =========================
    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveReport(
            @PathVariable long id,
            @RequestBody Map<String, String> request) {

        ReportingHeader report =
                reportingHeaderRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Report not found"));

        String comment = request.get("comment");

        report.setStatus("APPROVED");
        report.setReviewedAt(LocalDateTime.now());
        report.setReviewedBy("System Admin");
        report.setApprovalComment(comment);

        reportingHeaderRepository.save(report);

        return ResponseEntity.ok("Report Approved");
    }

    // =========================
    // REJECT REPORT
    // =========================
    @PutMapping("/reject/{id}")
    public ResponseEntity<?> rejectReport(
            @PathVariable long id,
            @RequestBody Map<String, String> request) {

        ReportingHeader report =
                reportingHeaderRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Report not found"));

        String comment = request.get("comment");

        report.setStatus("REJECTED");
        report.setReviewedAt(LocalDateTime.now());
        report.setReviewedBy("System Admin");
        report.setRejectionReason(comment);

        reportingHeaderRepository.save(report);

        return ResponseEntity.ok("Report Rejected");
    }
}