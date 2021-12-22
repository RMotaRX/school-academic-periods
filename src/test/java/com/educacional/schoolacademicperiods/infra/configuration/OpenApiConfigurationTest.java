package com.educacional.schoolacademicperiods.infra.configuration;

import com.educacional.schoolacademicperiods.application.i18n.Message;
import com.educacional.schoolacademicperiods.application.mapper.SchoolAcademicPeriodsMapper;
import com.educacional.schoolacademicperiods.application.rest.handler.ApiExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = OpenApiConfiguration.class)
@ContextConfiguration(classes = {Message.class, ApiExceptionHandler.class, ModelMapper.class, SchoolAcademicPeriodsMapper.class})
public class OpenApiConfigurationTest {


    @SpyBean
    private OpenApiConfiguration openApiConfiguration;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnSchoolAcademicPeriodsNotFoundException() {

        var openApi = openApiConfiguration.openAPIDefinition();
        assertEquals("API Applications - Educacional Positivo", openApi.getInfo().getTitle());
        assertEquals("APIs responsible for managing all informations about applications", openApi.getInfo().getDescription());
        assertFalse(openApi.getServers().isEmpty());
    }
}