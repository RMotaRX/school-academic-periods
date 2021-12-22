package com.educacional.schoolacademicperiods.application.mapper;

import com.educacional.schoolacademicperiods.application.rest.request.AcademicYearQueryFilterRequest;
import com.educacional.schoolacademicperiods.application.rest.request.AcademicYearRequest;
import com.educacional.schoolacademicperiods.application.rest.request.UpdateAcademicYearRequest;
import com.educacional.schoolacademicperiods.application.rest.response.AcademicYearResponse;
import com.educacional.schoolacademicperiods.domain.model.AcademicYear;
import com.educacional.schoolacademicperiods.domain.model.AcademicYearQueryFilter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SchoolAcademicPeriodsMapper {
  private final ModelMapper modelMapper;

  public AcademicYear toAcademicYear(String schoolId, AcademicYearRequest academicYearRequest) {
    var academicYear = modelMapper.map(academicYearRequest, AcademicYear.class);
    academicYear.setSchoolId(schoolId);

    return academicYear;
  }

  public AcademicYearResponse toAcademicYearResponse(AcademicYear academicYear) {
    return modelMapper.map(academicYear, AcademicYearResponse.class);
  }

  public List<AcademicYearResponse> toAcademicYearResponse(List<AcademicYear> academicYears) {
    return Optional.ofNullable(academicYears)
            .orElseGet(Collections::emptyList)
            .parallelStream()
            .map(this::toAcademicYearResponse)
            .collect(Collectors.toList());
  }

  public AcademicYearQueryFilter toAcademicYearQueryFilter(AcademicYearQueryFilterRequest academicYearQueryFilterRequest) {
    return modelMapper.map(academicYearQueryFilterRequest, AcademicYearQueryFilter.class);
  }

  public AcademicYear toAcademicYear(UpdateAcademicYearRequest updateAcademicYearRequest) {return modelMapper.map(updateAcademicYearRequest, AcademicYear.class);
  }
}
