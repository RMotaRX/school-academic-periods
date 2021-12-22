package com.educacional.schoolacademicperiods.infra.configuration;

import com.educacional.schoolacademicperiods.domain.client.OrganizationClient;
import com.educacional.schoolacademicperiods.domain.client.SchoolClassClient;
import com.educacional.schoolacademicperiods.domain.mapper.AcademicYearDomainMapper;
import com.educacional.schoolacademicperiods.domain.mapper.SchoolAcademicPeriodDomainMapper;
import com.educacional.schoolacademicperiods.domain.repository.SchoolAcademicPeriodsRepository;
import com.educacional.schoolacademicperiods.domain.repository.SchoolAcademicPeriodsYearRepository;
import com.educacional.schoolacademicperiods.domain.service.SchoolAcademicPeriodsYearService;
import com.educacional.schoolacademicperiods.infra.commons.DeletedRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BeanConfigurationTest {
    @Mock
    SchoolAcademicPeriodsYearRepository schoolAcademicPeriodsYearRepository;
    @Mock
    OrganizationClient organizationClient;
    @Mock
    AcademicYearDomainMapper academicYearDomainMapper;
    @Mock
    DeletedRecord deletedRecord;
    @Mock
    private SchoolAcademicPeriodsRepository schoolAcademicPeriodsRepository;
    @Mock
    SchoolClassClient schoolClassClient;
    @Mock
    SchoolAcademicPeriodDomainMapper schoolAcademicPeriodDomainMapper;
    @Mock
    SchoolAcademicPeriodsYearService schoolAcademicPeriodsYearService;
    @InjectMocks
    private BeanConfiguration beanConfiguration;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void schoolAcademicPeriodsYearService() {
        beanConfiguration.schoolAcademicPeriodsYearService(schoolAcademicPeriodsYearRepository, organizationClient, academicYearDomainMapper, deletedRecord);
    }

    @Test
    void schoolAcademicPeriodsService() {
        beanConfiguration.schoolAcademicPeriodsService(schoolAcademicPeriodsRepository, organizationClient, schoolAcademicPeriodsYearService, deletedRecord, schoolClassClient, schoolAcademicPeriodDomainMapper);
    }
}