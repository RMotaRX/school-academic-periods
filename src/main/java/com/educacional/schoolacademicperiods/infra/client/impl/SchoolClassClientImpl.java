package com.educacional.schoolacademicperiods.infra.client.impl;

import com.educacional.schoolacademicperiods.domain.client.SchoolClassClient;
import com.educacional.schoolacademicperiods.domain.model.SchoolClass;
import com.educacional.schoolacademicperiods.infra.client.SchoolClassFeignClient;
import com.educacional.schoolacademicperiods.infra.client.mapper.SchoolClassQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SchoolClassClientImpl implements SchoolClassClient {
    private final SchoolClassFeignClient schoolClassFeignClient;
    private final SchoolClassQueryMapper schoolClassQueryMapper;

    @Override
    public List<SchoolClass> getSchoolClass(String schoolId, SchoolClass schoolClass) {
        return schoolClassFeignClient.getSchoolClass(schoolId,schoolClassQueryMapper.toSchoolClassClientQueryParams(schoolClass));
    }
}
