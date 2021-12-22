package com.educacional.schoolacademicperiods.infra.feign;

import com.educacional.schoolacademicperiods.domain.exception.OrganizationNotFoundException;
import com.educacional.schoolacademicperiods.domain.exception.OrganizationServiceUnavailableException;
import com.educacional.schoolacademicperiods.domain.exception.ResourceNotFoundException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrganizationFeignErrorDecoderTest {

  @InjectMocks private OrganizationFeignErrorDecoder organizationFeignErrorDecoder;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldReturnOrganizationNotFoundException() {

    var request =
        Request.create(
            Request.HttpMethod.GET,
            "",
            new HashMap<>(),
            Request.Body.empty(),
            new RequestTemplate());
    var feignResponse = feign.Response.builder().status(404).request(request).build();

    var response = organizationFeignErrorDecoder.decode("", feignResponse);

    assertEquals(OrganizationNotFoundException.class, response.getClass());
  }

  @Test
  void shouldReturnOrganizationServiceUnavailableException() {

    var request =
        Request.create(
            Request.HttpMethod.GET,
            "",
            new HashMap<>(),
            Request.Body.empty(),
            new RequestTemplate());
    var feignResponse = feign.Response.builder().status(500).request(request).build();

    var response = organizationFeignErrorDecoder.decode("", feignResponse);

    assertEquals(OrganizationServiceUnavailableException.class, response.getClass());
  }
}
