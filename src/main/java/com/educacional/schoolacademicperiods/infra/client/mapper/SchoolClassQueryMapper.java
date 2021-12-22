package com.educacional.schoolacademicperiods.infra.client.mapper;

import com.educacional.schoolacademicperiods.domain.model.SchoolClass;
import com.educacional.schoolacademicperiods.infra.client.parameters.SchoolClassQueryParams;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchoolClassQueryMapper {
    private final ModelMapper modelMapper;

    public SchoolClassQueryParams toSchoolClassClientQueryParams(SchoolClass schoolClass) {
        return modelMapper.map(schoolClass, SchoolClassQueryParams.class);
    }

}
