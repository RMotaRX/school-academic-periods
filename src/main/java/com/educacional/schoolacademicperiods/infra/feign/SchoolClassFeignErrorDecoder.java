package com.educacional.schoolacademicperiods.infra.feign;

import com.educacional.schoolacademicperiods.domain.exception.ExceptionCode;
import com.educacional.schoolacademicperiods.domain.exception.SchoolClassNotFoundException;
import com.educacional.schoolacademicperiods.domain.exception.SchoolClassServiceUnavailableException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class SchoolClassFeignErrorDecoder extends FeignErrorDecoder{
    @Override
    public Exception decode(String s, Response response){
        if(response.status() == 404){
            return new SchoolClassNotFoundException(ExceptionCode.SCHOOL_CLASS_NOT_FOUND);
        }

        if(response.status() >=500 && response.status() <=599){
            return new SchoolClassServiceUnavailableException(ExceptionCode.SCHOOL_CLASS_SERVICE_UNAVAILABLE_EXCEPTION);
        }

        return new ErrorDecoder.Default().decode(s,response);
    }
}