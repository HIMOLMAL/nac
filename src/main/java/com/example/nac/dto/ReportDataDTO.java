package com.example.nac.dto;

public class ReportDataDTO {

    // ✅ FIX: match your entity relationship
    private Long headerId;

    private String programArea;
    private String indicator;
    private String modeOfApproach;
    private String ageGroup;
    private String gender;
    private String populationType;
    private Integer value;
    private String comment;

    public ReportDataDTO() {}

    public ReportDataDTO(Long headerId, String programArea, String indicator,
                         String modeOfApproach, String ageGroup, String gender,
                         String populationType, Integer value, String comment) {
        this.headerId = headerId;
        this.programArea = programArea;
        this.indicator = indicator;
        this.modeOfApproach = modeOfApproach;
        this.ageGroup = ageGroup;
        this.gender = gender;
        this.populationType = populationType;
        this.value = value;
        this.comment = comment;
    }

    // ✅ getters & setters
    public Long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(Long headerId) {
        this.headerId = headerId;
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