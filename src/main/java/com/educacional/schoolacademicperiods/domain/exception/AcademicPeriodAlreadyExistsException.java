package com.educacional.schoolacademicperiods.domain.exception;

import org.springframework.http.HttpStatus;

public class AcademicPeriodAlreadyExistsException extends BusinessException {

    private ExceptionCode code = ExceptionCode.ACADEMIC_PERIOD_ALREADY_EXISTS;
    private static final HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;

    public AcademicPeriodAlreadyExistsException(String message, ExceptionCode code) {
        super(message, code);
        this.code = code;
    }

    public AcademicPeriodAlreadyExistsException(ExceptionCode code) {
        super("Class Level not found", code);
        this.code = code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
