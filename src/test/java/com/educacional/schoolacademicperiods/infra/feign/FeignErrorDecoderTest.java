package com.educacional.schoolacademicperiods.infra.feign;

import com.educacional.schoolacademicperiods.domain.exception.ResourceNotFoundException;
import com.educacional.schoolacademicperiods.domain.exception.ServiceUnavailableException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeignErrorDecoderTest {

  @InjectMocks private FeignErrorDecoder feignErrorDecoder;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldReturnResourceNotFoundException() {

    var request =
        Request.create(
            Request.HttpMethod.GET,
            "",
            new HashMap<>(),
            Request.Body.empty(),
            new RequestTemplate());
    var feignResponse = feign.Response.builder().status(404).request(request).build();

    var response = feignErrorDecoder.decode("", feignResponse);

    assertEquals(ResourceNotFoundException.class, response.getClass());
  }

  @Test
  void shouldReturnServiceUnavailableException() {

    var request =
        Request.create(
            Request.HttpMethod.GET,
            "",
            new HashMap<>(),
            Request.Body.empty(),
            new RequestTemplate());
    var feignResponse = feign.Response.builder().status(500).request(request).build();

    var response = feignErrorDecoder.decode("", feignResponse);

    assertEquals(ServiceUnavailableException.class, response.getClass());
  }
}
