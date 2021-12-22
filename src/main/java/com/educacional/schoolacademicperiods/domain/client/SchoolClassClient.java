package com.educacional.schoolacademicperiods.domain.client;

import com.educacional.schoolacademicperiods.domain.model.SchoolClass;

import java.util.List;

public interface SchoolClassClient {
    List<SchoolClass> getSchoolClass(String schoolId, SchoolClass schoolClass);
}
