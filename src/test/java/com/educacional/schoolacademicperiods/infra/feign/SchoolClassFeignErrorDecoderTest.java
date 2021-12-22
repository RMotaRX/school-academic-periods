package com.educacional.schoolacademicperiods.infra.feign;

import com.educacional.schoolacademicperiods.domain.exception.SchoolClassNotFoundException;
import com.educacional.schoolacademicperiods.domain.exception.SchoolClassServiceUnavailableException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SchoolClassFeignErrorDecoderTest {

  @InjectMocks SchoolClassFeignErrorDecoder schoolClassFeignErrorDecoder;

  @BeforeEach
  void setup() {MockitoAnnotations.openMocks(this);}

  @Test
    void shouldReturnSchoolClassNotFoundException() {

      var request =
              Request.create(
                      Request.HttpMethod.GET,
                      "",
                      new HashMap<>(),
                      Request.Body.empty(),
                      new RequestTemplate());
      var feignResponse = feign.Response.builder().status(404).request(request).build();

      var response = schoolClassFeignErrorDecoder.decode("", feignResponse);

      assertEquals(SchoolClassNotFoundException.class, response.getClass());
  }

    @Test
    void shouldReturnSchoolClassServiceUnavailableException() {

        var request =
                Request.create(
                        Request.HttpMethod.GET,
                        "",
                        new HashMap<>(),
                        Request.Body.empty(),
                        new RequestTemplate());
        var feignResponse = feign.Response.builder().status(500).request(request).build();

        var response = schoolClassFeignErrorDecoder.decode("", feignResponse);

        assertEquals(SchoolClassServiceUnavailableException.class, response.getClass());
    }
}
