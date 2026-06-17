package com.example.nac.service;

import com.example.nac.dto.DashboardDTO;
import com.example.nac.entity.ReportData;
import com.example.nac.entity.ReportingHeader;
import com.example.nac.repository.ReportDataRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class DashBoardService {

    private final ReportDataRepository reportDataRepository;

    public DashBoardService(ReportDataRepository reportDataRepository) {
        this.reportDataRepository = reportDataRepository;
    }

    public DashboardDTO getDashboard(
            String program,
            String province,
            String gender,
            String month,
            LocalDate from,
            LocalDate to
    ) {

        List<ReportData> reportData =
                reportDataRepository.filterData(
                        program, null, province, gender,
                        null, null, null,
                        from, to, month
                );

        // =========================
        // CLEAN OUTPUT OBJECT
        // =========================
        DashboardDTO dto = new DashboardDTO();

        Map<String, Long> programTotals = new HashMap<>();
        Map<String, Long> provinceTotals = new HashMap<>();
        Map<String, Long> genderTotals = new HashMap<>();
        Map<String, Long> monthlyTotals = new HashMap<>();
        Map<String, Long> agencyTotals = new HashMap<>();

        long totalBeneficiaries = 0L;

        Set<String> sites = new HashSet<>();
        Set<String> provinces = new HashSet<>();
        Set<Long> reports = new HashSet<>();

        for (ReportData item : reportData) {

            long value = item.getValue() == null ? 0L : item.getValue().longValue();

            totalBeneficiaries += value;

            if (item.getHeader() != null) {
                reports.add(item.getHeader().getId());

                ReportingHeader h = item.getHeader();

                if (h.getProvince() != null) {
                    provinceTotals.put(
                            h.getProvince(),
                            provinceTotals.getOrDefault(h.getProvince(), 0L) + value
                    );
                    provinces.add(h.getProvince());
                }

                if (h.getReportingMonth() != null) {
                    monthlyTotals.put(
                            h.getReportingMonth(),
                            monthlyTotals.getOrDefault(h.getReportingMonth(), 0L) + value
                    );
                }

                if (h.getTypeOfAgency() != null) {
                    agencyTotals.put(
                            h.getTypeOfAgency(),
                            agencyTotals.getOrDefault(h.getTypeOfAgency(), 0L) + value
                    );
                }

                if (h.getSiteName() != null) {
                    sites.add(h.getSiteName());
                }
            }

            if (item.getProgramArea() != null) {
                programTotals.put(
                        item.getProgramArea(),
                        programTotals.getOrDefault(item.getProgramArea(), 0L) + value
                );
            }

            if (item.getGender() != null) {
                genderTotals.put(
                        item.getGender(),
                        genderTotals.getOrDefault(item.getGender(), 0L) + value
                );
            }
        }

        // =========================
        // DTO MAPPING (ONLY HERE)
        // =========================

        dto.setTotalReports((long) reports.size());
        dto.setTotalBeneficiaries(totalBeneficiaries);
        dto.setReportingSites((long) sites.size());
        dto.setReportingProvinces((long) provinces.size());

        dto.setProgramTotals(programTotals);
        dto.setProvinceTotals(provinceTotals);
        dto.setGenderTotals(genderTotals);
        dto.setMonthlyTotals(monthlyTotals);
        dto.setAgencyTotals(agencyTotals);

        return dto;
    }
}