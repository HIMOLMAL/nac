package com.example.nac.dto;

import java.util.Map;

public class DashboardDTO {

    private Long totalReports;
    private Long totalBeneficiaries;
    private Long reportingSites;
    private Long reportingProvinces;

    private Map<String, Long> programTotals;
    private Map<String, Long> provinceTotals;
    private Map<String, Long> genderTotals;
    private Map<String, Long> monthlyTotals;
    private Map<String, Long> agencyTotals;

    // getters + setters (unchanged)

    public Long getTotalReports() {
        return totalReports;
    }
    public void setTotalReports(Long totalReports) {
        this.totalReports = totalReports;
    }
    public Long getTotalBeneficiaries() {
        return totalBeneficiaries;
    }
    public void setTotalBeneficiaries(Long totalBeneficiaries) {
        this.totalBeneficiaries = totalBeneficiaries;
    }
    public Long getReportingSites() {
        return reportingSites;
    }
    public void setReportingSites(Long reportingSites) {
        this.reportingSites = reportingSites;
    }
    public Long getReportingProvinces() {
        return reportingProvinces;
    }
    public void setReportingProvinces(Long reportingProvinces) {
        this.reportingProvinces = reportingProvinces;
    }
    public Map<String, Long> getProgramTotals() {
        return programTotals;
    }
    public void setProgramTotals(Map<String, Long> programTotals) {
        this.programTotals = programTotals;
    }
    public Map<String, Long> getProvinceTotals() {
        return provinceTotals;
    }
    public void setProvinceTotals(Map<String, Long> provinceTotals) {
        this.provinceTotals = provinceTotals;
    }
    public Map<String, Long> getGenderTotals() {
        return genderTotals;
    }
    public void setGenderTotals(Map
<String, Long> genderTotals) {
        this.genderTotals = genderTotals;
    }
    public Map<String, Long> getMonthlyTotals() {
        return monthlyTotals;
    }
    public void setMonthlyTotals(Map<String, Long> monthlyTotals) {
        this.monthlyTotals = monthlyTotals;
    }
    public Map<String, Long> getAgencyTotals() {
        return agencyTotals;
    }
    public void setAgencyTotals(Map<String, Long> agencyTotals) {
        this.agencyTotals = agencyTotals;
    }
}