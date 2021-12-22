package com.educacional.schoolacademicperiods.domain.mapper;

import com.educacional.schoolacademicperiods.domain.model.AcademicYear;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@RequiredArgsConstructor
public class AcademicYearDomainMapper {

    private final ModelMapper modelMapper;

    public void merge(AcademicYear source, AcademicYear destination) {
        modelMapper.map(source, destination);
    }


}
