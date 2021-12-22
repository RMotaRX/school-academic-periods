package com.educacional.schoolacademicperiods.application.rest.controller;

import com.educacional.schoolacademicperiods.application.i18n.Message;
import com.educacional.schoolacademicperiods.application.i18n.MessageProperties;
import com.educacional.schoolacademicperiods.application.mapper.SchoolAcademicPeriodsMapper;
import com.educacional.schoolacademicperiods.application.rest.handler.ApiExceptionHandler;
import com.educacional.schoolacademicperiods.application.rest.response.AcademicYearResponse;
import com.educacional.schoolacademicperiods.application.rest.response.ErrorResponse;
import com.educacional.schoolacademicperiods.domain.enumeration.OrganizationType;
import com.educacional.schoolacademicperiods.domain.exception.ExceptionCode;
import com.educacional.schoolacademicperiods.domain.exception.InternalServiceException;
import com.educacional.schoolacademicperiods.domain.exception.OrganizationNotFoundException;
import com.educacional.schoolacademicperiods.domain.exception.OrganizationServiceUnavailableException;
import com.educacional.schoolacademicperiods.domain.exception.ResourceNotFoundException;
import com.educacional.schoolacademicperiods.domain.model.AcademicYear;
import com.educacional.schoolacademicperiods.domain.model.PageDomain;
import com.educacional.schoolacademicperiods.domain.model.impl.PageDomainImpl;
import com.educacional.schoolacademicperiods.domain.service.SchoolAcademicPeriodsYearService;
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
import java.util.UUID;

import static com.educacional.schoolacademicperiods.domain.exception.ExceptionCode.ACADEMIC_YEAR_NOT_FOUND;
import static com.educacional.schoolacademicperiods.domain.exception.ExceptionCode.API_FIELDS_INVALID;
import static com.educacional.schoolacademicperiods.domain.exception.ExceptionCode.INTERNAL_SERVER_ERROR;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodYearTemplate.*;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodYearTemplate.ACADEMIC_YEAR_ID;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodYearTemplate.FIELD_YEAR;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodYearTemplate.SCHOOL_ID;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodYearTemplate.getAcademicYearRequestTemplate;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodYearTemplate.getAcademicYearTemplate;
import static com.educacional.schoolacademicperiods.templates.SchoolAcademicPeriodYearTemplate.getYearUpdateStatusRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
@SpringBootTest(classes = SchoolAcademicPeriodsYearController.class)
@ContextConfiguration(
    classes = {
      Message.class,
      ApiExceptionHandler.class,
      ModelMapper.class,
      SchoolAcademicPeriodsMapper.class
    })
class SchoolAcademicPeriodsYearControllerTest {
  @Autowired MockMvc mockMvc;

  @Autowired private Message message;

  @MockBean
  SchoolAcademicPeriodsYearService schoolAcademicPeriodsYearService;

  private final String SCHOOL_ACADEMIC_PERIOD_BASE_URL = "/v1/school-academic-periods/";
  private final String SCHOOL_ACADEMIC_PERIOD_URL = "/years/";

  @Test
  void shouldCreateSchoolAcademicPeriodYearGivenValidRequest() throws Exception {
    when(schoolAcademicPeriodsYearService.createAcademicYear(any()))
        .thenReturn(getAcademicYearTemplate());

    var mvcResult =
        PerformRequest.post(
                mockMvc,
                SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + SCHOOL_ACADEMIC_PERIOD_URL,
                getAcademicYearRequestTemplate())
            .andExpect(status().isOk())
            .andReturn();

    var applicationResponse =
        JsonMapper.asObject(
            mvcResult.getResponse().getContentAsString(), AcademicYearResponse.class);

    assertNotNull(applicationResponse.getId());
    verify(schoolAcademicPeriodsYearService, times(1)).createAcademicYear(any());
  }

  @Test
  void shouldUpdateYearStatusGivenValidRequest() throws Exception {
    var updateStatus = getYearUpdateStatusRequest();
    var url = SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + SCHOOL_ACADEMIC_PERIOD_URL +"/"+ACADEMIC_YEAR_ID + "/status";

    PerformRequest.patch(mockMvc, url, updateStatus)
            .andExpect(status().isNoContent())
            .andReturn();

  }

  @Test
  void shouldReturnResourceNotFoundExceptionInUpdateYearStatus() throws Exception {
    var updateStatus = getYearUpdateStatusRequest();
    var url = SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + SCHOOL_ACADEMIC_PERIOD_URL +"/"+ACADEMIC_YEAR_ID + "/status";


    doThrow(new ResourceNotFoundException(ACADEMIC_YEAR_NOT_FOUND)).when(schoolAcademicPeriodsYearService).updateYearStatus(any(),any(),any());

    var mvcResult = PerformRequest.patch(mockMvc, url, updateStatus)
            .andExpect(status().isNotFound()).andReturn();
    var errorResponse = JsonMapper.asObject(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);
    assertEquals(ACADEMIC_YEAR_NOT_FOUND.toString(), errorResponse.getCode());

    assertEquals("Academic year not found", errorResponse.getMessage());
    verify(schoolAcademicPeriodsYearService, times(1)).updateYearStatus(any(),any(),any());
  }

  @Test
  void shouldReturnBadRequestGivenInvalidFields() throws Exception {

    var invalidFields = List.of(FIELD_YEAR);

    var createApplicationRequest = getAcademicYearRequestTemplate().withYear(null);

    var mvcResult =
        PerformRequest.post(
                mockMvc,
                SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + SCHOOL_ACADEMIC_PERIOD_URL,
                createApplicationRequest)
            .andExpect(status().isBadRequest())
            .andReturn();

    var errorResponse =
        JsonMapper.asObject(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);

    validateInvalidFields(invalidFields, errorResponse);
  }

  @Test
  void shouldReturnUnprocessableEntityGivenOrganizationNotFound() throws Exception {

    doThrow(
            new OrganizationNotFoundException(
                ExceptionCode.ORGANIZATION_NOT_FOUND,
                OrganizationType.SCHOOL.getValue(),
                SCHOOL_ID))
        .when(schoolAcademicPeriodsYearService)
        .createAcademicYear(any());

    var mvcResult =
        PerformRequest.post(
                mockMvc,
                SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + SCHOOL_ACADEMIC_PERIOD_URL,
                getAcademicYearRequestTemplate())
            .andExpect(status().isUnprocessableEntity())
            .andReturn();

    var errorResponse =
        JsonMapper.asObject(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);

    validateErrorResponse(
        errorResponse,
        MessageProperties.ORGANIZATION_NOT_FOUND,
        OrganizationType.SCHOOL.getValue(),
        SCHOOL_ID);
  }

  @Test
  void shouldReturnBadGatewayGivenOrganizationUnavailable() throws Exception {
    doThrow(
            new OrganizationServiceUnavailableException(
                ExceptionCode.ORGANIZATION_SERVICE_UNAVAILABLE_EXCEPTION))
        .when(schoolAcademicPeriodsYearService)
        .createAcademicYear(any());

    var mvcResult =
        PerformRequest.post(
                mockMvc,
                SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + SCHOOL_ACADEMIC_PERIOD_URL,
                getAcademicYearRequestTemplate())
            .andExpect(status().isBadGateway())
            .andReturn();

    var errorResponse =
        JsonMapper.asObject(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);

    validateErrorResponse(
        errorResponse, MessageProperties.ORGANIZATION_SERVICE_UNAVAILABLE_EXCEPTION);
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

    validateErrorResponse(errorResponse, MessageProperties.API_BODY_INVALID);
  }

  @Test
  void shouldReturnBadRequestGivenConstraintViolationException() throws Exception {
    Set<? extends ConstraintViolation<?>> violations = new HashSet<>();

    when(schoolAcademicPeriodsYearService.createAcademicYear(any(AcademicYear.class)))
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

    validateErrorResponse(errorResponse, MessageProperties.API_FIELDS_INVALID);
  }

  @Test
  void shouldReturnAcademicYearGivenValidId() throws Exception {
    var url =
        SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + SCHOOL_ACADEMIC_PERIOD_URL + ACADEMIC_YEAR_ID;

    when(schoolAcademicPeriodsYearService.findAcademicYear(any(), any()))
        .thenReturn(getAcademicYearTemplate());

    var mvcResult = PerformRequest.get(mockMvc, url).andExpect(status().isOk()).andReturn();

    var academicYearResponse =
        JsonMapper.asObject(
            mvcResult.getResponse().getContentAsString(), AcademicYearResponse.class);

    verify(schoolAcademicPeriodsYearService, times(1)).findAcademicYear(any(), any());
  }

  @Test
  void shouldReturnNotFoundHttpStatusGivenInvalidId() throws Exception {
    var url =
        SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + SCHOOL_ACADEMIC_PERIOD_URL + ACADEMIC_YEAR_ID;

    doThrow(new ResourceNotFoundException(ACADEMIC_YEAR_NOT_FOUND))
        .when(schoolAcademicPeriodsYearService)
        .findAcademicYear(any(), any());

    var mvcResult = PerformRequest.get(mockMvc, url).andExpect(status().isNotFound()).andReturn();

    var errorResponse =
        JsonMapper.asObject(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);

    verify(schoolAcademicPeriodsYearService, times(1)).findAcademicYear(any(), any());
    validateErrorResponse(errorResponse, MessageProperties.ACADEMIC_YEAR_NOT_FOUND);
  }

  private void validateErrorResponse(
      ErrorResponse errorResponse, MessageProperties messageError, String... customMessageFields) {

    String[] customMessages = customMessageFields;
    assertNotNull(errorResponse);
    assertEquals(messageError.toString(), errorResponse.getCode());
    assertEquals(errorResponse.getMessage(), message.get(messageError.toString(), customMessages));
  }

  private void validateInvalidFields(List<String> invalidFields, ErrorResponse errorResponse) {
    assertNotNull(errorResponse);
    assertEquals(MessageProperties.API_FIELDS_INVALID.toString(), errorResponse.getCode());
    assertEquals(errorResponse.getMessage(), message.get(MessageProperties.API_FIELDS_INVALID));
    assertNotNull(errorResponse.getDetails());
    assertEquals(invalidFields.size(), errorResponse.getDetails().size());
    errorResponse
        .getDetails()
        .forEach(field -> assertTrue(invalidFields.contains(field.getField())));
  }

  @Test
  public static PageDomain<AcademicYear> getAcademicYearPageDomain() {
    List<AcademicYear> academicYears = getListofAcademicYearTemplate();
    return new PageDomainImpl<AcademicYear>(academicYears, academicYears.size(), 1);
  }

  @Test
  void shouldReturnAPI_FIELDS_INVALID_Expection_GivenInvalidSchoolId() throws Exception {
    var schoolId = "123";
    var id = getAcademicYearTemplate().getId();
    var url = SCHOOL_ACADEMIC_PERIOD_BASE_URL + schoolId + SCHOOL_ACADEMIC_PERIOD_URL;

    doThrow(new AssertionError(INTERNAL_SERVER_ERROR))
            .when(schoolAcademicPeriodsYearService).findAllAcademicYearResponseBySchoolId(any(), any());

    var mvcResult = PerformRequest.get(mockMvc, url)
            .andExpect(status().isInternalServerError())
            .andReturn();

    var errorResponse = JsonMapper.asObject(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);

    assertNotNull(errorResponse.getMessage());
    assertEquals(INTERNAL_SERVER_ERROR.toString(), errorResponse.getCode());
    assertNotNull(errorResponse.getTimestamp());
    verify(schoolAcademicPeriodsYearService, times(1)).findAllAcademicYearResponseBySchoolId(any(), any());
  }

  @Test
  void shouldReturnAPI_FIELDS_INVALID_ExceptionGivenInvalidSortFieldParams() throws Exception {
    var id = getAcademicYearTemplate().getId();
    var url = SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + "/years?page=0&limit=10&sort=ERRO&sort_type=ASC";

    doThrow(new AssertionError(API_FIELDS_INVALID))
            .when(schoolAcademicPeriodsYearService).findAllAcademicYearResponseBySchoolId(any(), any());

    var mvcResult = PerformRequest.get(mockMvc, url)
            .andExpect(status().isBadRequest())
            .andReturn();

    var errorResponse = JsonMapper.asObject(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);

    assertNotNull(errorResponse.getMessage());
    assertEquals(API_FIELDS_INVALID.toString(), errorResponse.getCode());
    assertNotNull(errorResponse.getTimestamp());
    verify(schoolAcademicPeriodsYearService, times(0)).findAllAcademicYearResponseBySchoolId(any(), any());
  }

  @Test
  void shouldReturnListofAcademicYearGivenValidParams() throws Exception {
    var url =
            SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + SCHOOL_ACADEMIC_PERIOD_URL;

    when(schoolAcademicPeriodsYearService.findAllAcademicYearResponseBySchoolId(any(), any()))
            .thenReturn(getListofPageDomainAcademicYearTemplate());

    var mvcResult = PerformRequest.get(mockMvc, url).andExpect(status().isOk()).andReturn();

    verify(schoolAcademicPeriodsYearService, times(1)).findAllAcademicYearResponseBySchoolId(any(), any());

  }

  @Test
  void shouldUpdateDisciplineGivenValidParemeters() throws Exception {
    var academicYearUpdateTemplate = getAcademicYearUpdateTemplate();
    var url =
            SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + SCHOOL_ACADEMIC_PERIOD_URL + ACADEMIC_YEAR_ID;
    PerformRequest.put(mockMvc, url, academicYearUpdateTemplate).andExpect(status().isNoContent());

    verify(schoolAcademicPeriodsYearService, times(1)).updateAcademicYear(any(),any(),any());
  }

  @Test
  void shouldReturnInternalServiceException() throws Exception {
    var url = SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + SCHOOL_ACADEMIC_PERIOD_URL + ACADEMIC_YEAR_ID;

    doThrow(new InternalServiceException(ExceptionCode.INTERNAL_SERVER_ERROR)).when(schoolAcademicPeriodsYearService).deleteAcademicYear(any(),any());
    var mvcResult = PerformRequest.delete(mockMvc, url).andExpect(status().isInternalServerError()).andReturn();

    var errorResponse = JsonMapper.asObject(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);

    assertEquals("An internal server error has occurred", errorResponse.getMessage());
    verify(schoolAcademicPeriodsYearService, times(1)).deleteAcademicYear(any(), any());
  }

  @Test
  void shouldDeleteSchoolAcademicYear() throws Exception {
    var url = SCHOOL_ACADEMIC_PERIOD_BASE_URL + SCHOOL_ID + SCHOOL_ACADEMIC_PERIOD_URL + ACADEMIC_YEAR_ID;

    PerformRequest.delete(mockMvc, url).andExpect(status().isNoContent());

    verify(schoolAcademicPeriodsYearService, times(1)).deleteAcademicYear(any(),any());
  }

}
