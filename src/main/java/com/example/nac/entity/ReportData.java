package com.example.nac.entity;




import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "report_data")
public class ReportData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================
    // RELATION TO HEADER
    // =========================
    
    
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "header_id", nullable = false)
   @JsonIgnoreProperties("reportDataList")


    private ReportingHeader header;

    // =========================
    // CORE DATA
    // =========================
    private String programArea;  
    // HIV Awareness, Referrals, Condoms, etc.

    private String indicator;    
    // e.g. "People reached", "Condoms distributed"

    private String modeOfApproach; 
    // Community, Schools, Prisons, Workplace, etc.

    private String ageGroup;     
    // 0–14, 15–24, 25–34, 35–49, 50+

    private String gender;       
    // M, F, TG, MSM, MSW, FSW

    private String populationType;
    // General, Key Population, etc.

    private Integer value;       
    // numeric value from your grid

    private String comment;

    

    // =========================
    // GETTERS & SETTERS
    // =========================

    public Long getId() {
        return id;
    }

    public ReportingHeader getHeader() {
        return header;
    }

    public void setHeader(ReportingHeader header) {
        this.header = header;
    }

    public String getProgramArea() {
        return programArea;
    }

    public void setProgramArea(String programArea) {
        this.programArea = programArea;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getModeOfApproach() {
        return modeOfApproach;
    }

    public void setModeOfApproach(String modeOfApproach) {
        this.modeOfApproach = modeOfApproach;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPopulationType() {
        return populationType;
    }

    public void setPopulationType(String populationType) {
        this.populationType = populationType;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}