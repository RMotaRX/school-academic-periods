package com.educacional.schoolacademicperiods.domain.repository;

import com.educacional.schoolacademicperiods.domain.model.AcademicPeriodQueryFilter;
import com.educacional.schoolacademicperiods.domain.model.PageDomain;
import com.educacional.schoolacademicperiods.domain.model.SchoolAcademicPeriods;

import java.util.Optional;

public interface SchoolAcademicPeriodsRepository {
    SchoolAcademicPeriods save(SchoolAcademicPeriods schoolAcademicPeriods);

    PageDomain<SchoolAcademicPeriods> findAllAcademicResponseBySchoolId(String schoolId, AcademicPeriodQueryFilter academicPeriodQueryFilter);

    Optional<SchoolAcademicPeriods> findAcademicPeriodBySchoolIdAndId(String schoolId, String id);

    void delete(SchoolAcademicPeriods academicPeriod);
}

