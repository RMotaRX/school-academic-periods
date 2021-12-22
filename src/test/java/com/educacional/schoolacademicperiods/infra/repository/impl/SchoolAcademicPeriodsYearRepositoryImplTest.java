package com.educacional.schoolacademicperiods.infra.repository.impl;

import com.educacional.schoolacademicperiods.domain.model.AcademicYear;
import com.educacional.schoolacademicperiods.infra.repository.MongoDbSchoolAcademicYearRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodYearTemplate.getAcademicYearTemplate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class SchoolAcademicPeriodsYearRepositoryImplTest {

  @Mock private MongoDbSchoolAcademicYearRepository mongoDbSchoolAcademicYearRepository;

  @Mock private MongoTemplate mongoTemplate;

  @InjectMocks private SchoolAcademicPeriodsYearRepositoryImpl schoolAcademicPeriodsRepository;

  @BeforeEach
  void setup() {
    openMocks(this);
  }

  @Test
  void shouldSaveAcademicYearGivenValidObject() {
    when(mongoDbSchoolAcademicYearRepository.save(any(AcademicYear.class)))
        .thenReturn(any(AcademicYear.class));
    schoolAcademicPeriodsRepository.save(getAcademicYearTemplate());
    verify(mongoDbSchoolAcademicYearRepository, times(1)).save(any());
  }

  @Test
  void shouldFindAcademicYearGivenValidParameters() {
    when(mongoDbSchoolAcademicYearRepository.findAcademicYearBySchoolIdAndId(any(), any()))
        .thenReturn(Optional.of(getAcademicYearTemplate()));
    schoolAcademicPeriodsRepository.findBySchoolIdAndId(any(), any());
    verify(mongoDbSchoolAcademicYearRepository, times(1))
        .findAcademicYearBySchoolIdAndId(any(), any());
  }


}
