package com.educacional.schoolacademicperiods.domain.exception;

import org.springframework.http.HttpStatus;

public class OrganizationServiceUnavailableException extends BusinessException {
    private final ExceptionCode code;
    private static final HttpStatus httpStatus = HttpStatus.BAD_GATEWAY;

    public OrganizationServiceUnavailableException(String message, ExceptionCode code) {
        super(message, code);
        this.code = code;
    }

    public OrganizationServiceUnavailableException(ExceptionCode code) {
        super("Organization service is not available", code);
        this.code = code;
    }

    @Override
    public ExceptionCode getCode() {
        return code;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}


