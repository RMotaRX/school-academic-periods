package com.educacional.schoolacademicperiods.domain.repository;

import com.educacional.schoolacademicperiods.domain.model.AcademicYear;
import com.educacional.schoolacademicperiods.domain.model.AcademicYearQueryFilter;
import com.educacional.schoolacademicperiods.domain.model.PageDomain;

import java.util.Optional;

public interface SchoolAcademicPeriodsYearRepository {

  AcademicYear save(AcademicYear academicYear);

  Optional<AcademicYear> findBySchoolIdAndId(String schoolId, String id);

  PageDomain<AcademicYear> findAllAcademicYearResponseBySchoolId(AcademicYearQueryFilter academicYearQueryFilter, String schoolId);

    void delete(AcademicYear academicYear);
}
