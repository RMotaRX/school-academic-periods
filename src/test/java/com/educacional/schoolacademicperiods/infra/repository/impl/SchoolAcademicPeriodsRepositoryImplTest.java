package com.educacional.schoolacademicperiods.infra.repository.impl;

import com.educacional.schoolacademicperiods.domain.client.OrganizationClient;
import com.educacional.schoolacademicperiods.domain.mapper.AcademicYearDomainMapper;
import com.educacional.schoolacademicperiods.domain.model.SchoolAcademicPeriods;
import com.educacional.schoolacademicperiods.domain.repository.SchoolAcademicPeriodsRepository;
import com.educacional.schoolacademicperiods.infra.repository.MongoDbSchoolAcademicPeriodsRepository;
import com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.time.Instant;
import java.util.Optional;

import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodTemplate.CREATED_AT_END;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodTemplate.CREATED_AT_START;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodTemplate.ID;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodTemplate.SCHOOL_ID;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodTemplate.getAcademicPeriodQueryFilter;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodTemplate.getListSchoolAcademicPeriodTemplate;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodTemplate.getPageDomanin;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodTemplate.getScholAcademicPeriods;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SchoolAcademicPeriodsRepositoryImplTest {


    @Mock
    private OrganizationClient organizationClient;

    @Mock
    private MongoTemplate mongoTemplate;
    @Mock
    private AcademicYearDomainMapper academicYearDomainMapper;
    @Mock
    private SchoolAcademicPeriodsRepository schoolAcademicPeriodsRepository;
    @Mock
    private MongoDbSchoolAcademicPeriodsRepository mongoDbSchoolAcademicPeriodsRepository;

    @InjectMocks
    private SchoolAcademicPeriodsRepositoryImpl schoolAcademicPeriodsRepositoryImpl;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByFilter() {
        when(mongoTemplate.find(any(Query.class), eq(SchoolAcademicPeriods.class))).thenReturn(getPageDomanin().getContent());
        when(mongoTemplate.count(any(Query.class), eq(SchoolAcademicPeriods.class))).thenReturn(Long.valueOf(getPageDomanin().getContent().size()));
        var schoolAcademicPeriods = schoolAcademicPeriodsRepositoryImpl.findAllAcademicResponseBySchoolId(SCHOOL_ID,getAcademicPeriodQueryFilter());

        assertNotNull(schoolAcademicPeriods);
        assertFalse(schoolAcademicPeriods.getContent().isEmpty());
        assertTrue(schoolAcademicPeriods.getTotal() > 0);
    }

    @Test
    void save() {
        var schoolAcademicPeriod = getScholAcademicPeriods();
        when(mongoDbSchoolAcademicPeriodsRepository.save(any())).thenReturn(schoolAcademicPeriod);
        this.schoolAcademicPeriodsRepositoryImpl.save(schoolAcademicPeriod);
        verify(mongoDbSchoolAcademicPeriodsRepository, times(1)).save(any());
    }

    @Test
    void delete() {
        var schoolAcademicPeriod = getScholAcademicPeriods();
        this.schoolAcademicPeriodsRepositoryImpl.delete(schoolAcademicPeriod);
        verify(mongoDbSchoolAcademicPeriodsRepository, times(1)).delete(any());
    }

    @Test
    void findById() {
        var schoolAcademicPeriod = getScholAcademicPeriods();
        when(mongoDbSchoolAcademicPeriodsRepository.findAcademicPeriodBySchoolIdAndId(any(), any())).thenReturn(Optional.of(schoolAcademicPeriod));
        this.schoolAcademicPeriodsRepositoryImpl.findAcademicPeriodBySchoolIdAndId(SCHOOL_ID, ID);
        verify(mongoDbSchoolAcademicPeriodsRepository, times(1)).findAcademicPeriodBySchoolIdAndId(any(), any());
    }

    @Test
    void shouldFindSchoolAcademicPeriodGivenValidQueryFilter() {
        var periodRequest = getAcademicPeriodQueryFilter();
        var schoolAcademicPeriod = getPageDomanin();

        when(mongoTemplate.count(any(Query.class), eq(SchoolAcademicPeriods.class))).thenReturn(Long.valueOf(schoolAcademicPeriod.getContent().size()));
        when(mongoTemplate.find(any(Query.class), eq(SchoolAcademicPeriods.class))).thenReturn(getListSchoolAcademicPeriodTemplate());

        var academicPeriodsPageDomain = schoolAcademicPeriodsRepositoryImpl.findAllAcademicResponseBySchoolId(SCHOOL_ID, periodRequest);

        assertNotNull(academicPeriodsPageDomain);
        assertTrue(academicPeriodsPageDomain.getTotal() > 0);
        assertEquals(academicPeriodsPageDomain.getTotal(), academicPeriodsPageDomain.getContent().size());

    }

    @Test
    void shouldFindAcademicPeriodGivenValidQueryFilterCreatedAtStart() {
        var periodRequest = getAcademicPeriodQueryFilter();
        periodRequest.setCreatedAtStart(Instant.parse(CREATED_AT_START));

        var schoolAcademicPeriod = getPageDomanin();

        when(mongoTemplate.count(any(Query.class), eq(SchoolAcademicPeriods.class))).thenReturn(Long.valueOf(schoolAcademicPeriod.getContent().size()));
        when(mongoTemplate.find(any(Query.class), eq(SchoolAcademicPeriods.class))).thenReturn(getListSchoolAcademicPeriodTemplate());

        var academicPeriodsPageDomain = schoolAcademicPeriodsRepositoryImpl.findAllAcademicResponseBySchoolId(SCHOOL_ID, periodRequest);

        assertNotNull(academicPeriodsPageDomain);
        assertTrue(academicPeriodsPageDomain.getTotal() > 0);
        assertEquals(academicPeriodsPageDomain.getTotal(), academicPeriodsPageDomain.getContent().size());

    }

    @Test
    void shouldFindAcademicPeriodGivenValidQueryFilterCreatedAtEnd() {
        var periodRequest = getAcademicPeriodQueryFilter();
        periodRequest.setCreatedAtStart(Instant.parse(CREATED_AT_END));

        var schoolAcademicPeriod = getPageDomanin();

        when(mongoTemplate.count(any(Query.class), eq(SchoolAcademicPeriods.class))).thenReturn(Long.valueOf(schoolAcademicPeriod.getContent().size()));
        when(mongoTemplate.find(any(Query.class), eq(SchoolAcademicPeriods.class))).thenReturn(getListSchoolAcademicPeriodTemplate());

        var academicPeriodsPageDomain = schoolAcademicPeriodsRepositoryImpl.findAllAcademicResponseBySchoolId(SCHOOL_ID, periodRequest);

        assertNotNull(academicPeriodsPageDomain);
        assertTrue(academicPeriodsPageDomain.getTotal() > 0);
        assertEquals(academicPeriodsPageDomain.getTotal(), academicPeriodsPageDomain.getContent().size());
    }







}