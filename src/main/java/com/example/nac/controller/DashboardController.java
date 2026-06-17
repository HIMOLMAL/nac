package com.example.nac.controller;

import com.example.nac.dto.DashboardDTO;
import com.example.nac.entity.ReportData;
import com.example.nac.entity.ReportingHeader;
import com.example.nac.repository.ReportDataRepository;

import jakarta.annotation.PostConstruct;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
public class DashboardController {

    private ReportDataRepository reportDataRepository;

    public DashboardController(ReportDataRepository reportDataRepository) {
        this.reportDataRepository = reportDataRepository;
    }

    // ✅ DEBUG: confirm controller is loaded
    @PostConstruct
    public void init() {
        System.out.println("✅ DashboardController LOADED");
    }

    // ✅ TEST ENDPOINT (VERY IMPORTANT FOR YOUR ERROR)
    @GetMapping("/test")
    public String test() {
        return "Dashboard API WORKING";
    }

    @GetMapping
    public DashboardDTO getDashboard(

            @RequestParam(required = false) String program,
            @RequestParam(required = false) String province,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String month,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to
    ) {

        List<ReportData> reportData =
                reportDataRepository.filterData(
                        program,
                        null,
                        province,
                        gender,
                        null,
                        null,
                        null,
                        from,
                        to,
                        month
                );

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

            long value = (item.getValue() == null) ? 0 : item.getValue();

            totalBeneficiaries += value;

            if (item.getHeader() != null) {
                reports.add(item.getHeader().getId());
            }

            // Program Area
            if (item.getProgramArea() != null) {
                programTotals.put(
                        item.getProgramArea(),
                        programTotals.getOrDefault(item.getProgramArea(), 0L) + value
                );
            }

            // Gender
            if (item.getGender() != null) {
                genderTotals.put(
                        item.getGender(),
                        genderTotals.getOrDefault(item.getGender(), 0L) + value
                );
            }

            if (item.getHeader() != null) {

                ReportingHeader h = item.getHeader();

                // Province
                if (h.getProvince() != null) {
                    provinceTotals.put(
                            h.getProvince(),
                            provinceTotals.getOrDefault(h.getProvince(), 0L) + value
                    );
                    provinces.add(h.getProvince());
                }

                // Month
                if (h.getReportingMonth() != null) {
                    monthlyTotals.put(
                            h.getReportingMonth(),
                            monthlyTotals.getOrDefault(h.getReportingMonth(), 0L) + value
                    );
                }

                // Agency
                if (h.getTypeOfAgency() != null) {
                    agencyTotals.put(
                            h.getTypeOfAgency(),
                            agencyTotals.getOrDefault(h.getTypeOfAgency(), 0L) + value
                    );
                }

                // Sites
                if (h.getSiteName() != null) {
                    sites.add(h.getSiteName());
                }
            }
        }

        dto.setTotalReports ((long) reports.size());
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