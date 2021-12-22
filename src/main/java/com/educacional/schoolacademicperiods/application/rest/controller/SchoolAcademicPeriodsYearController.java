package com.educacional.schoolacademicperiods.application.rest.controller;

import com.educacional.schoolacademicperiods.application.mapper.SchoolAcademicPeriodsMapper;
import com.educacional.schoolacademicperiods.application.rest.enumeration.HttpHeaders;
import com.educacional.schoolacademicperiods.application.rest.request.AcademicYearQueryFilterRequest;
import com.educacional.schoolacademicperiods.application.rest.request.AcademicYearRequest;
import com.educacional.schoolacademicperiods.application.rest.request.UpdateAcademicYearRequest;
import com.educacional.schoolacademicperiods.application.rest.request.UpdateYearStatusRequest;
import com.educacional.schoolacademicperiods.application.rest.response.AcademicYearResponse;
import com.educacional.schoolacademicperiods.application.rest.spec.SchoolAcademicPeriodsYearControllerSpec;
import com.educacional.schoolacademicperiods.domain.service.SchoolAcademicPeriodsYearService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/school-academic-periods/{schoolId}/years")
@RequiredArgsConstructor
public class SchoolAcademicPeriodsYearController implements SchoolAcademicPeriodsYearControllerSpec {

    private final SchoolAcademicPeriodsYearService schoolAcademicPeriodsYearService;
    private final SchoolAcademicPeriodsMapper schoolAcademicPeriodsMapper;

    @Value("${rest.pageable.default.acceptable-range}")
    private String acceptableRange;

    @Override
    @PostMapping
    public AcademicYearResponse createAcademicYear(
            @PathVariable final String schoolId,
            @RequestBody @Valid AcademicYearRequest academicYearRequest) {

        var academicYear =
                schoolAcademicPeriodsYearService.createAcademicYear(
                        schoolAcademicPeriodsMapper.toAcademicYear(schoolId, academicYearRequest));

        return schoolAcademicPeriodsMapper.toAcademicYearResponse(academicYear);
    }

    @GetMapping("/{yearId}")
    public AcademicYearResponse findAcademicYearResponse(
            @PathVariable final String schoolId, @PathVariable final String yearId) {
        var academicYear = schoolAcademicPeriodsYearService.findAcademicYear(schoolId, yearId);
        return schoolAcademicPeriodsMapper.toAcademicYearResponse(academicYear);
    }

    @PatchMapping("/{yearId}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable String schoolId, @PathVariable String yearId, @RequestBody @Valid UpdateYearStatusRequest updateYearStatusRequest) {
        schoolAcademicPeriodsYearService.updateYearStatus(schoolId, yearId, updateYearStatusRequest);
    }

    @GetMapping
    public ResponseEntity<List<AcademicYearResponse>> findAllAcademicYearResponseBySchoolId(
            @Valid AcademicYearQueryFilterRequest academicYearQueryFilterRequest, @PathVariable final String schoolId) {
        var academicYears = schoolAcademicPeriodsYearService
                .findAllAcademicYearResponseBySchoolId(schoolAcademicPeriodsMapper.toAcademicYearQueryFilter(academicYearQueryFilterRequest), schoolId);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_RANGE.getName(), String.valueOf(academicYears.getTotal()))
                .header(HttpHeaders.ACCEPT_RANGES.getName(), acceptableRange)
                .header("Access-Control-Expose-Headers", "*, Content-Range, Total-Pages")
                .header(HttpHeaders.TOTAL_PAGES.getName(), String.valueOf(academicYears.getTotalPages()))
                .body(schoolAcademicPeriodsMapper.toAcademicYearResponse(academicYears.getContent()));
    }

    @PutMapping("/{yearId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAcademicYear(
            @PathVariable final String yearId,
            @PathVariable final String schoolId,
            @RequestBody @Valid UpdateAcademicYearRequest updateAcademicYearRequest) {
        schoolAcademicPeriodsYearService.updateAcademicYear(yearId, schoolId, schoolAcademicPeriodsMapper.toAcademicYear(updateAcademicYearRequest));
    }

    @DeleteMapping("/{yearId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAcademicYear (@PathVariable final String yearId, @PathVariable final String schoolId) {
        schoolAcademicPeriodsYearService.deleteAcademicYear(yearId, schoolId);
    }
}
