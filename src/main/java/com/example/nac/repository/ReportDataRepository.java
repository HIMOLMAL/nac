package com.example.nac.repository;

import com.example.nac.entity.ReportData;
import com.example.nac.entity.ReportingHeader;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface ReportDataRepository extends JpaRepository<ReportData, Long> {

    // ✅ FIXED METHOD
    List<ReportData> findByHeaderId(Long headerId);
    List<ReportData> findByHeader(ReportingHeader header);

 @Query("""
SELECT r FROM ReportData r
WHERE (:programarea IS NULL OR LOWER(r.programArea) LIKE LOWER(CONCAT('%', :programarea, '%')))
AND (:indicator IS NULL OR LOWER(r.indicator) LIKE LOWER(CONCAT('%', :indicator, '%')))
AND (:province IS NULL OR LOWER(r.header.province) LIKE LOWER(CONCAT('%', :province, '%')))
AND (:gender IS NULL OR (r.gender IS NOT NULL AND LOWER(r.gender) = LOWER(:gender)))
AND (:mode IS NULL OR LOWER(r.modeOfApproach) LIKE LOWER(CONCAT('%', :mode, '%')))
AND (:agency IS NULL OR LOWER(r.header.typeOfAgency) LIKE LOWER(CONCAT('%', :agency, '%')))
AND (:user IS NULL OR LOWER(r.header.user.username) LIKE LOWER(CONCAT('%', :user, '%')))
AND (:from IS NULL OR r.header.reportDate >= :from)
AND (:to IS NULL OR r.header.reportDate <= :to)
AND (:month IS NULL OR LOWER(r.header.reportingMonth) = LOWER(:month))
""")
List<ReportData> filterData(
        @Param("programarea") String program,
        @Param("indicator") String indicator,
        @Param("province") String province,
        @Param("gender") String gender,
        @Param("mode") String mode,
        @Param("agency") String agency,
        @Param("user") String user,
        @Param("from") LocalDate from,
        @Param("to") LocalDate to,
        @Param("month") String month
);
}