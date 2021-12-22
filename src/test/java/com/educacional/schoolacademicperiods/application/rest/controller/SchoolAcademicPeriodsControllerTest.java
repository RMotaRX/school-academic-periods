package com.educacional.schoolacademicperiods.application.rest.controller;

import com.educacional.schoolacademicperiods.application.i18n.Message;
import com.educacional.schoolacademicperiods.application.mapper.SchoolAcademicMapper;
import com.educacional.schoolacademicperiods.application.rest.handler.ApiExceptionHandler;
import com.educacional.schoolacademicperiods.application.rest.response.ErrorResponse;
import com.educacional.schoolacademicperiods.application.rest.response.SchoolAcademicPeriodsResponse;
import com.educacional.schoolacademicperiods.domain.enumeration.OrganizationType;
import com.educacional.schoolacademicperiods.domain.exception.AcademicPeriodLinkedSchoolClassException;
import com.educacional.schoolacademicperiods.domain.exception.ExceptionCode;
import com.educacional.schoolacademicperiods.domain.exception.InternalServiceException;
import com.educacional.schoolacademicperiods.domain.exception.OrganizationNotFoundException;
import com.educacional.schoolacademicperiods.domain.exception.SchoolClassNotFoundException;
import com.educacional.schoolacademicperiods.domain.service.SchoolAcademicPeriodsService;
import com.educacional.schoolacademicperiods.utils.JsonMapper;
import com.educacional.schoolacademicperiods.utils.PerformRequest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.educacional.schoolacademicperiods.domain.exception.ExceptionCode.API_FIELDS_INVALID;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodTemplate.ID;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodTemplate.PERIOD_ID;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodTemplate.getAcademicPeriodRequest;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodTemplate.getPageDomanin;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodTemplate.getScholAcademicPeriods;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodYearTemplate.ACADEMIC_YEAR_ID;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodTemplate.getUpdateAcademicStatusRequest;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodTemplate.getUpdateSchoolAcademicPeriod;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodYearTemplate.SCHOOL_ID;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodYearTemplate.getAcademicYearRequestTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodYearTemplate.getAcademicYearTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableAutoConfiguration(
        exclude = {
                MongoAutoConfiguration.class,
                MongoDataAutoConfiguration.class,
                MongoRepositoriesAutoConfiguration.class
        })
@AutoConfigureMockMvc
@RequiredArgsConstructor
@SpringBootTest(classes = SchoolAcademicPeriodsController.class)
@ContextConfiguration(
        classes = {
                Message.class,
                ApiExceptionHandler.class,
                ModelMapper.class,
                SchoolAcademicMapper.class
        })
public class SchoolAcademicPeriodsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private Message message;

    @MockBean
    SchoolAcademicPeriodsService schoolAcademicPeriodsService;

    private final String SCHOOL_ACADEMIC_PERIOD_BASE_URL = "/v1/school-academic-periods/";
    private final String SCHOOL_ACADEMIC_PERIOD_URL = "/periods";

    @Test
    void shouldCreateSchoolAcademicPeriodGivenValidRequest() throws Exception {
        when(schoolAcademicPeriodsService.createAcademicPeriod(any(), any()))
                .thenReturn(getScholAcademicPeriods());

        var mvcResult =
                PerformRequest.post(
                                mockMvc,
                                SCHOOL_ACADEMIC_PERIOD_BASE_URL + ID + SCHOOL_ACADEMIC_PERIOD_URL,
                                getAcademicPeriodRequest())
                        .andExpect(status().isOk())
                        .andReturn();

        var response =
                JsonMapper.asObject(
                        mvcResult.getResponse().getContentAsString(), SchoolAcademicPeriodsResponse.class);

        assertNotNull(response);
        verify(schoolAcademicPeriodsService, times(1)).createAcademicPeriod(any(), any());
    }

    @Test
    void shouldReturnBadRequestGivenInvalidFields() throws Exception {

        var createAcademicPeriodRequest = getAcademicPeriodRequest().withYearId(null);

        var mvcResult =
                PerformRequest.post(
                                mockMvc,
                                SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + SCHOOL_ACADEMIC_PERIOD_URL,
                                createAcademicPeriodRequest)
                        .andExpect(status().isBadRequest())
                        .andReturn();

        JsonMapper.asObject(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);
    }

    @Test
    void shouldReturnUnprocessableEntityGivenOrganizationNotFound() throws Exception {

        doThrow(
                new OrganizationNotFoundException(
                        ExceptionCode.ORGANIZATION_NOT_FOUND,
                        OrganizationType.SCHOOL.getValue(),
                        SCHOOL_ID))
                .when(schoolAcademicPeriodsService)
                .createAcademicPeriod(any(), any());

        PerformRequest.post(
                        mockMvc,
                        SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + SCHOOL_ACADEMIC_PERIOD_URL,
                        getAcademicPeriodRequest())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();
    }

    @Test
    void shouldReturnBadRequestGivenEmptyRequestBody() throws Exception {
        var mvcResult =
                PerformRequest.post(
                                mockMvc,
                                SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + SCHOOL_ACADEMIC_PERIOD_URL,
                                "")
                        .andExpect(status().isBadRequest())
                        .andReturn();

        var errorResponse =
                JsonMapper.asObject(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);

    }

    @Test
    void shouldReturnBadRequestGivenConstraintViolationException() throws Exception {
        Set<? extends ConstraintViolation<?>> violations = new HashSet<>();

        when(schoolAcademicPeriodsService.createAcademicPeriod(any(), any()))
                .thenThrow(new ConstraintViolationException(violations));

        var mvcResult =
                PerformRequest.post(
                                mockMvc,
                                SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + SCHOOL_ACADEMIC_PERIOD_URL,
                                getAcademicYearRequestTemplate())
                        .andExpect(status().isBadRequest())
                        .andReturn();

        var errorResponse =
                JsonMapper.asObject(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);
    }


    @Test
    void shouldReturnAcademicPeriodGivenValidId() throws Exception {
        var url = SCHOOL_ACADEMIC_PERIOD_BASE_URL + "/" + ID + SCHOOL_ACADEMIC_PERIOD_URL + "/" + ID;
        when(schoolAcademicPeriodsService.findAcademicPeriod(any(), any())).thenReturn(getScholAcademicPeriods());

        var mvcResult = PerformRequest.get(mockMvc, url, ID)
                .andExpect(status().isOk())
                .andReturn();

        JsonMapper.asObject(mvcResult.getResponse().getContentAsString(), SchoolAcademicPeriodsResponse.class);

        verify(schoolAcademicPeriodsService, times(1)).findAcademicPeriod(any(), any());


    }

    @Test
    void shouldReturnInternalServiceException() throws Exception {
        var url = SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + SCHOOL_ACADEMIC_PERIOD_URL + "/" + ID;

        doThrow(new InternalServiceException(ExceptionCode.INTERNAL_SERVER_ERROR)).when(schoolAcademicPeriodsService).deleteAcademicPeriod(any(),any());
        var mvcResult = PerformRequest.delete(mockMvc, url).andExpect(status().isInternalServerError()).andReturn();

        var errorResponse = JsonMapper.asObject(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);

        assertEquals("An internal server error has occurred", errorResponse.getMessage());
        verify(schoolAcademicPeriodsService, times(1)).deleteAcademicPeriod(any(), any());
    }

    @Test
    void shouldDeleteSchoolAcademicPeriod() throws Exception {
        var url = SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + SCHOOL_ACADEMIC_PERIOD_URL + "/" + ID;

        PerformRequest.delete(mockMvc, url).andExpect(status().isNoContent());

        verify(schoolAcademicPeriodsService, times(1)).deleteAcademicPeriod(any(),any());
    }

    @Test
    void shouldReturnAcademicPeriodLinkedSchoolClassException() throws Exception {
        var url = SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + SCHOOL_ACADEMIC_PERIOD_URL + "/" + ID;

        doThrow(new AcademicPeriodLinkedSchoolClassException(ExceptionCode.ACADEMIC_PERIOD_LINKED_WITH_SCHOOL_CLASS))
                .when(schoolAcademicPeriodsService).deleteAcademicPeriod(any(), any());

        var mvcResult = PerformRequest.delete(mockMvc, url).andExpect(status().isUnprocessableEntity()).andReturn();
        var errorResponse = JsonMapper.asObject(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);

        assertEquals("This Academic Period have School Class linked.", errorResponse.getMessage());
        verify(schoolAcademicPeriodsService,times(1)).deleteAcademicPeriod(any(),any());
    }

    @Test
    void shouldReturnSchoolClassNotFoundException() throws Exception {
        var url = SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + SCHOOL_ACADEMIC_PERIOD_URL + "/" + ID;

        doThrow(new SchoolClassNotFoundException(ExceptionCode.SCHOOL_CLASS_NOT_FOUND)).when(schoolAcademicPeriodsService).deleteAcademicPeriod(any(),any());

        var mvcResult = PerformRequest.delete(mockMvc, url).andExpect(status().isUnprocessableEntity()).andReturn();
        var errorResponse = JsonMapper.asObject(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);

        assertEquals("School class not found.", errorResponse.getMessage());
        verify(schoolAcademicPeriodsService,times(1)).deleteAcademicPeriod(any(),any());

    }

    @Test
    void shouldUpdateSchoolAcademicPeriodGivenValidParameters() throws Exception {
        var updateSchoolPeriod = getUpdateSchoolAcademicPeriod();
        var url = SCHOOL_ACADEMIC_PERIOD_BASE_URL + "/" + ID + SCHOOL_ACADEMIC_PERIOD_URL + "/" + ID;
        when(schoolAcademicPeriodsService.findAcademicPeriod(any(), any())).thenReturn(getScholAcademicPeriods());

        PerformRequest.put(mockMvc, url, updateSchoolPeriod).andExpect(status().isNoContent());

        verify(schoolAcademicPeriodsService, times(1)).update(any());
    }


    @Test
    void shouldReturnListSchoolAcademic() throws Exception {
        var url = SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + "/periods?page=0&limit=10&sort_type=ASC";

        when(schoolAcademicPeriodsService.findAllAcademicResponseBySchoolId(any(), any())).thenReturn(getPageDomanin());

        var mvcResult = PerformRequest.get(mockMvc, url)
                .andExpect(status().isOk())
                .andReturn();

        var schoolAcademicList = JsonMapper.asObject(mvcResult.getResponse().getContentAsString(), List.class);
        assertNotNull(schoolAcademicList);
        assertFalse(schoolAcademicList.isEmpty());

        verify(schoolAcademicPeriodsService, times(1)).findAllAcademicResponseBySchoolId(any(), any());
    }

    @Test
    void shouldReturnAPI_FIELDS_INVALID_ExceptionGivenInvalidSortFieldParams() throws Exception {
        var id = getAcademicYearTemplate().getId();
        var url = SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + "/periods?page=0&limit=10&sort=id&sort_type=ASC";

        doThrow(new AssertionError(API_FIELDS_INVALID))
                .when(schoolAcademicPeriodsService).findAllAcademicResponseBySchoolId(any(), any());

        var mvcResult = PerformRequest.get(mockMvc, url)
                .andExpect(status().isBadRequest())
                .andReturn();

        var errorResponse = JsonMapper.asObject(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);

        assertNotNull(errorResponse.getMessage());
        assertEquals(API_FIELDS_INVALID.toString(), errorResponse.getCode());
        assertNotNull(errorResponse.getTimestamp());
        verify(schoolAcademicPeriodsService, times(0)).findAllAcademicResponseBySchoolId(any(), any());
    }

    @Test
    void shouldUpdateStatusAcademic() throws Exception {
        var updateAcademic = getUpdateAcademicStatusRequest();
        var url = SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + "/periods/" + PERIOD_ID + "/status";
        PerformRequest.patch(mockMvc, url, updateAcademic)
                .andExpect(status().isNoContent());
    }
}
