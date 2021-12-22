package com.educacional.schoolacademicperiods.infra.repository;

import com.educacional.schoolacademicperiods.domain.model.AcademicYear;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoDbSchoolAcademicYearRepository extends MongoRepository<AcademicYear, String> {
  Optional<AcademicYear> findAcademicYearBySchoolIdAndId(String schoolId, String id);
}
