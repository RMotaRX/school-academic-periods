package com.educacional.schoolacademicperiods.domain.exception;

import org.springframework.http.HttpStatus;

public class InternalServiceException extends BusinessException{
    private ExceptionCode code = ExceptionCode.INTERNAL_SERVER_ERROR;
    private static final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;


    public InternalServiceException(String message, ExceptionCode code) {
        super(message, code);
    }

    public InternalServiceException(ExceptionCode code) {
        super("An internal server error has occurred", code);
        this.code = code;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}