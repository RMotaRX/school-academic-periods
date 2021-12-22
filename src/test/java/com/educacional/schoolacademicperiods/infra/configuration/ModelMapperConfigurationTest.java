package com.educacional.schoolacademicperiods.infra.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

class ModelMapperConfigurationTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ModelMapperConfiguration modelMapperConfiguration;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        modelMapper=new ModelMapper();
    }

    @Test
    void testMapperBean(){
        modelMapperConfiguration.modelMapperBean();
    }

    @Test
    void testAcademicYearDomainMapper(){
        modelMapperConfiguration.academicYearDomainMapper(modelMapper);
    }

    @Test
    void testSchoolAcademicPeriodDomainMapper(){
        modelMapperConfiguration.schoolAcademicPeriodDomainMapper(modelMapper);
    }

}