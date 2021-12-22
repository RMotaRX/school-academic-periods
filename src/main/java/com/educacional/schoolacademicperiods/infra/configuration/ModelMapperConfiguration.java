package com.educacional.schoolacademicperiods.infra.configuration;

import com.educacional.schoolacademicperiods.application.rest.request.CreateSchoolAcademicPeriodsRequest;
import com.educacional.schoolacademicperiods.application.rest.request.UpdateSchoolAcademicPeriodRequest;
import com.educacional.schoolacademicperiods.domain.mapper.AcademicYearDomainMapper;
import com.educacional.schoolacademicperiods.domain.mapper.SchoolAcademicPeriodDomainMapper;
import com.educacional.schoolacademicperiods.domain.model.SchoolAcademicPeriods;
import com.educacional.schoolacademicperiods.infra.modelMapper.converter.LocalDateConverter;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class ModelMapperConfiguration {
    @Bean
    public ModelMapper modelMapperBean() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addConverter(new LocalDateConverter());
        addRequestToPeriodMapping(modelMapper);


        return modelMapper;
    }

    @Bean
    public AcademicYearDomainMapper academicYearDomainMapper(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return new AcademicYearDomainMapper(modelMapper);

    }

    @Bean
    public SchoolAcademicPeriodDomainMapper schoolAcademicPeriodDomainMapper(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return new SchoolAcademicPeriodDomainMapper(modelMapper);

    }

    private void addRequestToPeriodMapping(ModelMapper modelMapper) {
        var periodModelTypeMap = modelMapper.createTypeMap(
                CreateSchoolAcademicPeriodsRequest.class, SchoolAcademicPeriods.class);

        var periodUpdateModelTypeMap = modelMapper.createTypeMap(
                UpdateSchoolAcademicPeriodRequest.class, SchoolAcademicPeriods.class);

        periodModelTypeMap.<LocalDate>addMapping(
                CreateSchoolAcademicPeriodsRequest::getStartDate,
                (schoolAcademicPeriodsDest, value) -> schoolAcademicPeriodsDest.getPeriod().setStartDate(value));

        periodModelTypeMap.<LocalDate>addMapping(
                CreateSchoolAcademicPeriodsRequest::getEndDate,
                (schoolAcademicPeriodsDest, value) -> schoolAcademicPeriodsDest.getPeriod().setEndDate(value));

        periodUpdateModelTypeMap.<LocalDate>addMapping(
                UpdateSchoolAcademicPeriodRequest::getStartDate,
                (schoolAcademicPeriodsDest, value) -> schoolAcademicPeriodsDest.getPeriod().setStartDate(value));

        periodUpdateModelTypeMap.<LocalDate>addMapping(
                UpdateSchoolAcademicPeriodRequest::getEndDate,
                (schoolAcademicPeriodsDest, value) -> schoolAcademicPeriodsDest.getPeriod().setEndDate(value));
    }
}
