package com.educacional.schoolacademicperiods.domain.mapper;

import com.educacional.schoolacademicperiods.domain.model.SchoolAcademicPeriods;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@RequiredArgsConstructor
public class SchoolAcademicPeriodDomainMapper {
    private final ModelMapper modelMapper;

    public void merge(SchoolAcademicPeriods source, SchoolAcademicPeriods destination) {
        modelMapper.map(source, destination);
    }
}
