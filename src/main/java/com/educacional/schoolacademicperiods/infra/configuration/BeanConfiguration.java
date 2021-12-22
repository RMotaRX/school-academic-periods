package com.educacional.schoolacademicperiods.infra.configuration;

import com.educacional.schoolacademicperiods.application.mapper.SchoolAcademicPeriodsMapper;
import com.educacional.schoolacademicperiods.domain.client.OrganizationClient;
import com.educacional.schoolacademicperiods.domain.client.SchoolClassClient;
import com.educacional.schoolacademicperiods.domain.mapper.AcademicYearDomainMapper;
import com.educacional.schoolacademicperiods.domain.mapper.SchoolAcademicPeriodDomainMapper;
import com.educacional.schoolacademicperiods.domain.repository.SchoolAcademicPeriodsRepository;
import com.educacional.schoolacademicperiods.domain.repository.SchoolAcademicPeriodsYearRepository;
import com.educacional.schoolacademicperiods.domain.service.SchoolAcademicPeriodsService;
import com.educacional.schoolacademicperiods.domain.service.SchoolAcademicPeriodsYearService;
import com.educacional.schoolacademicperiods.infra.commons.DeletedRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public SchoolAcademicPeriodsYearService schoolAcademicPeriodsYearService(
            SchoolAcademicPeriodsYearRepository schoolAcademicPeriodsYearRepository,
            OrganizationClient organizationClient, AcademicYearDomainMapper academicYearDomainMapper, DeletedRecord deletedRecord) {
        return new SchoolAcademicPeriodsYearService(schoolAcademicPeriodsYearRepository, organizationClient, academicYearDomainMapper, deletedRecord);
    }


    @Bean
    public SchoolAcademicPeriodsService schoolAcademicPeriodsService(SchoolAcademicPeriodsRepository schoolAcademicPeriodsRepository, OrganizationClient organizationClient, SchoolAcademicPeriodsYearService schoolAcademicPeriodsYearService, DeletedRecord deletedRecord, SchoolClassClient schoolClassClient, SchoolAcademicPeriodDomainMapper schoolAcademicPeriodDomainMapper) {
        return new SchoolAcademicPeriodsService(schoolAcademicPeriodsRepository, organizationClient, schoolAcademicPeriodsYearService, deletedRecord, schoolClassClient, schoolAcademicPeriodDomainMapper);
    }
}
