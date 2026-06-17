package com.example.nac.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonFormat(pattern = "yyyy-MM-dd")
@Entity
@Table(name = "reporting_header")
public class ReportingHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================
    // ORIGINAL FIELDS
    // =========================
    private LocalDate reportDate;
    private String typeOfAgency;
    private String typeOfService;
    private String siteName;
    private String province;
    private String district;
    private String llg;
    private String contactNumber;
    private String reportingMonth;

    // =========================
    // USER RELATION
    // =========================
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // =========================
    // REPORT DATA CHILD TABLE
    // =========================
    @JsonIgnore
    @OneToMany(mappedBy = "header")
    private List<ReportData> reportDataList;

    // =========================
    // NEW: WORKFLOW STATUS (IMPORTANT)
    // =========================
    private String status;  
    // Values: PENDING, APPROVED, REJECTED

    // =========================
    // NEW: SUBMISSION TRACKING
    // =========================
    private LocalDateTime submittedAt;

    private LocalDateTime reviewedAt;

    // =========================
    // NEW: APPROVAL DETAILS
    // =========================
    private String reviewedBy; // officer name or email

    private String approvalComment;

    // Optional: useful for audit trail
    private String rejectionReason;

    // =========================
    // GETTERS & SETTERS
    // =========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public String getTypeOfAgency() {
        return typeOfAgency;
    }

    public void setTypeOfAgency(String typeOfAgency) {
        this.typeOfAgency = typeOfAgency;
    }

    public String getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(String typeOfService) {
        this.typeOfService = typeOfService;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLlg() {
        return llg;
    }

    public void setLlg(String llg) {
        this.llg = llg;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getReportingMonth() {
        return reportingMonth;
    }

    public void setReportingMonth(String reportingMonth) {
        this.reportingMonth = reportingMonth;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ReportData> getReportDataList() {
        return reportDataList;
    }

    public void setReportDataList(List<ReportData> reportDataList) {
        this.reportDataList = reportDataList;
    }

    // =========================
    // NEW GETTERS/SETTERS
    // =========================

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public String getApprovalComment() {
        return approvalComment;
    }

    public void setApprovalComment(String approvalComment) {
        this.approvalComment = approvalComment;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    // =========================
    // HELPER METHODS
    // =========================

    public void addReportData(ReportData data) {
        reportDataList.add(data);
        data.setHeader(this);
    }

    public void removeReportData(ReportData data) {
        reportDataList.remove(data);
        data.setHeader(null);
    }
}