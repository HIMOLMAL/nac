package com.example.nac.service;

import com.example.nac.entity.ReportData;
import com.example.nac.entity.ReportingHeader;
import com.example.nac.repository.ReportDataRepository;
import com.example.nac.repository.ReportingHeaderRepository;


import org.springframework.stereotype.Service;

import com.example.nac.dto.ReportDataDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    private final ReportDataRepository reportDataRepo;
    private final ReportingHeaderRepository reportingHeaderRepo;

    public ReportService(ReportDataRepository reportDataRepo, ReportingHeaderRepository reportingHeaderRepo) {
        this.reportDataRepo = reportDataRepo;
        this.reportingHeaderRepo = reportingHeaderRepo;
    }

    // =========================
    // SAVE SINGLE REPORT DATA
    // =========================
    public ReportData saveReportData(Long headerId, ReportData data) {

        if (headerId == null) {
            throw new RuntimeException("Header id is required");
        }

        ReportingHeader header = reportingHeaderRepo.findById(java.util.Objects.requireNonNull(headerId))
                .orElseThrow(() -> new RuntimeException("Reporting Header not found"));

        data.setHeader(header);

        return reportDataRepo.save(data);
    }

    // =========================
    // SAVE MULTIPLE REPORT DATA (BULK)
    // =========================
    public List<ReportData> saveAll(List<ReportDataDTO> dtoList) {

        List<ReportData> savedList = new ArrayList<>();

        for (ReportDataDTO dto : dtoList) {

            Long headerId = dto.getHeaderId();

                ReportingHeader header = reportingHeaderRepo.findById(java.util.Objects.requireNonNull(headerId, "Header id is required"))
                    .orElseThrow(() -> new RuntimeException("Header not found: " + headerId));

            ReportData data = new ReportData();
            data.setHeader(header);
            data.setProgramArea(dto.getProgramArea());
            data.setIndicator(dto.getIndicator());
            data.setModeOfApproach(dto.getModeOfApproach());
            data.setAgeGroup(dto.getAgeGroup());
            data.setGender(dto.getGender());
            data.setPopulationType(dto.getPopulationType());
            data.setValue(dto.getValue());
            data.setComment(dto.getComment());

            savedList.add(reportDataRepo.save(data));
        }

        return savedList;
    }

    // =========================
    // 🔥 NEW: GET REPORT DATA BY HEADER ID (THIS FIXES YOUR JS)
    // =========================
    public List<ReportData> getReportData(Long headerId) {

        if (headerId == null) {
            return reportDataRepo.findAll();
        }

        if (!reportingHeaderRepo.existsById(headerId)) {
            throw new RuntimeException("Header not found: " + headerId);
        }

        ReportingHeader header = reportingHeaderRepo.findById(java.util.Objects.requireNonNull(headerId, "Header id is required"))
            .orElseThrow(() -> new RuntimeException("Header not found: " + headerId));

        return reportDataRepo.findByHeader(header);
    }

    // =========================
    // CHECK IF HEADER EXISTS
    // =========================
    public boolean headerExists(Long headerId) {
        if (headerId == null) {
            return false;
        }
        return reportingHeaderRepo.existsById(headerId);
    }

    // =========================
    // DELETE REPORT DATA
    // =========================
    public void deleteReportData(Long id) {

        if (id == null) {
            throw new RuntimeException("Report data id is required");
        }

        reportDataRepo.deleteById(id);
    }
}