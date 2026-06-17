package com.example.nac.controller;

import com.example.nac.dto.ReportDataDTO;
import com.example.nac.entity.ReportData;
import com.example.nac.entity.ReportingHeader;
import com.example.nac.repository.ReportDataRepository;
import com.example.nac.repository.ReportingHeaderRepository;
import com.example.nac.service.ReportService;


import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/report-data")
public class ReportDataController {

    private final ReportDataRepository reportDataRepo;
    private final ReportingHeaderRepository reportingHeaderRepo;
    private final ReportService reportService;

    public ReportDataController(
            ReportDataRepository reportDataRepo,
            ReportingHeaderRepository reportingHeaderRepo,
            ReportService reportService) {
        this.reportDataRepo = reportDataRepo;
        this.reportingHeaderRepo = reportingHeaderRepo;
        this.reportService = reportService;
    }

    @Transactional
    @PostMapping("/save-all")
    public List<ReportData> saveAll(@RequestBody List<ReportDataDTO> dtoList) {

        // ✅ Prevent empty request crash
        if (dtoList == null || dtoList.isEmpty()) {
            throw new RuntimeException("No report data received");
        }

        // ✅ Get Header Once
        Long headerId = dtoList.get(0).getHeaderId();
        if (headerId == null) {
            throw new RuntimeException("Header ID cannot be null");
        }
        ReportingHeader header = reportingHeaderRepo.findById(headerId)
                .orElseThrow(() -> new RuntimeException(
                        "Header not found: " + headerId));

        // ✅ Build List
        List<ReportData> dataList = dtoList.stream().map(dto -> {

            ReportData data = new ReportData();

            data.setHeader(header);
            data.setProgramArea(dto.getProgramArea());
            data.setIndicator(dto.getIndicator());
            data.setModeOfApproach(dto.getModeOfApproach());
            data.setGender(dto.getGender());
            data.setAgeGroup(dto.getAgeGroup());
            data.setPopulationType(dto.getPopulationType());
            data.setValue(dto.getValue());
            data.setComment(dto.getComment());

            return data;

        }).toList();

        // ✅ Save All At Once
        if (dataList == null || dataList.isEmpty()) {
            throw new RuntimeException("No data to save");
        }
        return reportDataRepo.saveAll(dataList);
    }

    @PostMapping("/save/{headerId}")
    public ReportData saveReportData(
            @PathVariable Long headerId,
            @RequestBody ReportData data) {

        return reportService.saveReportData(headerId, data);
    }

       @GetMapping("/test")
public List<ReportData> test() {
    return reportDataRepo.findAll();
}

 @GetMapping("/all")
public List<ReportData> getAll() {

    List<ReportData> data = reportDataRepo.findAll();

    data.forEach(d -> {
        System.out.println(
            d.getId() + " | " +
            d.getProgramArea() + " | " +
            d.getIndicator() + " | " +
            d.getValue()
        );
    });

    return data;
}

@GetMapping("/status/pending")
public List<ReportingHeader> getPendingReports() {
    return reportingHeaderRepo.findByStatus("PENDING");
}

@GetMapping("/filter")
public List<ReportData> filterData(
        @RequestParam(required = false) String programArea,
        @RequestParam(required = false) String indicator,
        @RequestParam(required = false) String province,
        @RequestParam(required = false) String gender,
        @RequestParam(required = false) String mode,
        @RequestParam(required = false) String agency,
        @RequestParam(required = false) String user,
        @RequestParam(required = false) LocalDate from,
        @RequestParam(required = false) LocalDate to,
        @RequestParam(required = false) String month
) {

 

    programArea = (programArea == null || programArea.isBlank()) ? null : programArea;
    indicator = (indicator == null || indicator.isBlank()) ? null : indicator;
    province = (province == null || province.isBlank()) ? null : province;
    gender = (gender == null || gender.isBlank()) ? null : gender;
    mode = (mode == null || mode.isBlank()) ? null : mode;
    agency = (agency == null || agency.isBlank()) ? null : agency;
    user = (user == null || user.isBlank()) ? null : user;
    month = (month == null || month.isBlank()) ? null : month;

       System.out.println("MONTH RECEIVED = " + month);

    return reportDataRepo.filterData(
            programArea,
            indicator,
            province,
            gender,
            mode,
            agency,
            user,
            from,
            to,
            month
    );

    
}
}