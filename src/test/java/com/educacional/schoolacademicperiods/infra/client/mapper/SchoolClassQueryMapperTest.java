package com.educacional.schoolacademicperiods.infra.client.mapper;

import com.educacional.schoolacademicperiods.templates.SchoolClassTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

class SchoolClassQueryMapperTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SchoolClassQueryMapper schoolClassQueryMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldMapSchoolClassQueryParams() {
        this.schoolClassQueryMapper.toSchoolClassClientQueryParams(SchoolClassTemplate.getSchoolClassTemplate());
    }
}