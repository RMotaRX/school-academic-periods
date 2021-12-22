package com.educacional.schoolacademicperiods.infra.feign;

import com.educacional.schoolacademicperiods.domain.exception.BusinessException;
import com.educacional.schoolacademicperiods.domain.exception.ExceptionCode;
import com.educacional.schoolacademicperiods.domain.exception.OrganizationNotFoundException;
import com.educacional.schoolacademicperiods.domain.exception.OrganizationServiceUnavailableException;
import feign.Response;

public class OrganizationFeignErrorDecoder extends FeignErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 404) {
            return new OrganizationNotFoundException(ExceptionCode.ORGANIZATION_NOT_FOUND);
        }

        if (response.status() == 400) {
            return new BusinessException(ExceptionCode.API_FIELDS_INVALID, "invalid UUID format");
        }

        if (response.status() >= 500 && response.status() <= 599) {
            return new OrganizationServiceUnavailableException(ExceptionCode.ORGANIZATION_SERVICE_UNAVAILABLE_EXCEPTION);
        }

        return new Default().decode(s, response);
    }
}
