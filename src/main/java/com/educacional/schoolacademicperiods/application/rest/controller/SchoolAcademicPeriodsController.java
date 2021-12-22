package com.educacional.schoolacademicperiods.application.rest.controller;

import com.educacional.schoolacademicperiods.application.mapper.SchoolAcademicMapper;
import com.educacional.schoolacademicperiods.application.rest.enumeration.HttpHeaders;
import com.educacional.schoolacademicperiods.application.rest.request.AcademicPeriodQueryFilterRequest;
import com.educacional.schoolacademicperiods.application.rest.request.CreateSchoolAcademicPeriodsRequest;
import com.educacional.schoolacademicperiods.application.rest.request.UpdateSchoolAcademicPeriodRequest;
import com.educacional.schoolacademicperiods.application.rest.request.UpdateAcademicStatusRequest;
import com.educacional.schoolacademicperiods.application.rest.response.SchoolAcademicPeriodsResponse;
import com.educacional.schoolacademicperiods.application.rest.spec.SchoolAcademicPeriodsControllerSpec;
import com.educacional.schoolacademicperiods.domain.model.SchoolAcademicPeriods;
import com.educacional.schoolacademicperiods.domain.service.SchoolAcademicPeriodsService;
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
@RequestMapping(value = "/v1/school-academic-periods/{schoolId}/periods")
@RequiredArgsConstructor
public class SchoolAcademicPeriodsController implements SchoolAcademicPeriodsControllerSpec {

    private final SchoolAcademicMapper schoolAcademicMapper;
    private final SchoolAcademicPeriodsService schoolAcademicPeriodsService;

    @Value("${rest.pageable.default.acceptable-range}")
    private String acceptableRange;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public SchoolAcademicPeriodsResponse create(@RequestBody @Valid CreateSchoolAcademicPeriodsRequest createSchoolAcademicPeriodsRequest, @PathVariable String schoolId) {
        SchoolAcademicPeriods schoolAcademicPeriods = schoolAcademicPeriodsService.createAcademicPeriod(schoolAcademicMapper.toAcademicPeriods(createSchoolAcademicPeriodsRequest), schoolId);
        return schoolAcademicMapper.toschoolAcademicResponse(schoolAcademicPeriods);
    }

    @GetMapping
    public ResponseEntity<List<SchoolAcademicPeriodsResponse>> listSchoolAcademicPeriodsResponse(
            @PathVariable String schoolId, @Valid AcademicPeriodQueryFilterRequest academicPerioQueryFilterRequest) {
        var academicPageDomain = schoolAcademicPeriodsService
                .findAllAcademicResponseBySchoolId(schoolAcademicMapper.toacademicPerioQueryFilter(academicPerioQueryFilterRequest), schoolId);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_RANGE.getName(), String.valueOf(academicPageDomain.getTotal()))
                .header(HttpHeaders.ACCEPT_RANGES.getName(), acceptableRange)
                .header(HttpHeaders.TOTAL_PAGES.getName(), String.valueOf(academicPageDomain.getTotalPages()))
                .body(schoolAcademicMapper.toschoolsAcademicResponse(academicPageDomain.getContent()));
    }


    @GetMapping("/{periodId}")
    @ResponseStatus(HttpStatus.OK)
    public SchoolAcademicPeriodsResponse getDetails(@PathVariable String schoolId, @PathVariable String periodId) {
        SchoolAcademicPeriods schoolAcademicPeriods = schoolAcademicPeriodsService.findAcademicPeriod(schoolId, periodId);
        return schoolAcademicMapper.toschoolAcademicResponse(schoolAcademicPeriods);
    }

    @DeleteMapping("/{periodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAcademicPeriod(@PathVariable String schoolId, @PathVariable String periodId) {
        schoolAcademicPeriodsService.deleteAcademicPeriod(schoolId, periodId);
    }

    @PutMapping("/{periodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSchoolAcademicPeriod(@RequestBody @Valid UpdateSchoolAcademicPeriodRequest updateSchoolAcademicPeriod,
                                           @PathVariable String schoolId,
                                           @PathVariable String periodId) {
        var period = schoolAcademicMapper.toAcademicPeriods(updateSchoolAcademicPeriod);
        period.setSchoolId(schoolId);
        period.setId(periodId);
        schoolAcademicPeriodsService.update(period);
    }

    @PatchMapping("/{periodId}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable String schoolId, @PathVariable String periodId, @RequestBody @Valid UpdateAcademicStatusRequest updateAcademicStatusRequest) {
        schoolAcademicPeriodsService.updateStatus(schoolId, periodId, updateAcademicStatusRequest.getStatus());
    }

}
