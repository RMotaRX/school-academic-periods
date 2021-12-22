package com.educacional.schoolacademicperiods.infra.feign;

import com.educacional.schoolacademicperiods.domain.exception.ExceptionCode;
import com.educacional.schoolacademicperiods.domain.exception.ResourceNotFoundException;
import com.educacional.schoolacademicperiods.domain.exception.ServiceUnavailableException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 404) {
            return new ResourceNotFoundException(ExceptionCode.RESOURCE_NOT_FOUND);
        }

        if (response.status() >= 500 && response.status() <= 599) {
            return new ServiceUnavailableException(ExceptionCode.SERVICE_UNAVAILABLE_EXCEPTION);
        }

        return new Default().decode(s, response);
    }
}
