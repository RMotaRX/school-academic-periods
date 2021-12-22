package com.educacional.schoolacademicperiods.domain.exception;

import org.springframework.http.HttpStatus;

public class YearNotFoundException extends BusinessException{
    private final ExceptionCode code;
    private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public YearNotFoundException(String message, ExceptionCode code) {
        super(message, code);
        this.code = code;
    }

    public YearNotFoundException(ExceptionCode code) {
        super("Academic year not found", code);
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
