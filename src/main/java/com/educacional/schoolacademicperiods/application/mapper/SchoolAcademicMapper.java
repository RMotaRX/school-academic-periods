package com.educacional.schoolacademicperiods.application.mapper;

import com.educacional.schoolacademicperiods.application.rest.request.AcademicPeriodQueryFilterRequest;
import com.educacional.schoolacademicperiods.application.rest.request.CreateSchoolAcademicPeriodsRequest;
import com.educacional.schoolacademicperiods.application.rest.request.UpdateSchoolAcademicPeriodRequest;
import com.educacional.schoolacademicperiods.application.rest.response.SchoolAcademicPeriodsResponse;
import com.educacional.schoolacademicperiods.domain.model.AcademicPeriodQueryFilter;
import com.educacional.schoolacademicperiods.domain.model.SchoolAcademicPeriods;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class SchoolAcademicMapper {
    private final ModelMapper modelMapper;

    public SchoolAcademicPeriods toAcademicPeriods(CreateSchoolAcademicPeriodsRequest createSchoolAcademicPeriodsRequest) {
        return modelMapper.map(createSchoolAcademicPeriodsRequest, SchoolAcademicPeriods.class);
    }

    public SchoolAcademicPeriods toAcademicPeriods(UpdateSchoolAcademicPeriodRequest updateSchoolAcademicPeriod) {
        return modelMapper.map(updateSchoolAcademicPeriod, SchoolAcademicPeriods.class);
    }

    public SchoolAcademicPeriodsResponse toschoolAcademicResponse(SchoolAcademicPeriods schoolAcademicPeriods) {
        return modelMapper.map(schoolAcademicPeriods, SchoolAcademicPeriodsResponse.class);
    }

    public List<SchoolAcademicPeriodsResponse> toschoolsAcademicResponse(List<SchoolAcademicPeriods> schoolsAcademicPeriods) {
        return Optional.ofNullable(schoolsAcademicPeriods).
                orElse(Collections.emptyList())
                .stream()
                .map(this::toschoolAcademicResponse)
                .collect(Collectors.toList());
    }
    public AcademicPeriodQueryFilter toacademicPerioQueryFilter(AcademicPeriodQueryFilterRequest academicPerioQueryFilterRequest) {
        return modelMapper.map(academicPerioQueryFilterRequest, AcademicPeriodQueryFilter.class);
    }



}
