package com.educacional.schoolacademicperiods.domain.exception;

import org.springframework.http.HttpStatus;

public class SchoolClassServiceUnavailableException extends BusinessException {
    private ExceptionCode code = ExceptionCode.SCHOOL_CLASS_SERVICE_UNAVAILABLE_EXCEPTION;
    private static final HttpStatus httpStatus = HttpStatus.BAD_GATEWAY;


    public SchoolClassServiceUnavailableException(ExceptionCode code) {
        super("School class service is not available", code);
        this.code = code;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getCodeAsString() {
        return code.toString();
    }

}
