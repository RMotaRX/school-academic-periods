package com.educacional.schoolacademicperiods.infra.repository;

import com.educacional.schoolacademicperiods.domain.model.SchoolAcademicPeriods;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoDbSchoolAcademicPeriodsRepository extends MongoRepository<SchoolAcademicPeriods, String> {

    Optional<SchoolAcademicPeriods> findAcademicPeriodBySchoolIdAndId(String schoolId, String id);
}
