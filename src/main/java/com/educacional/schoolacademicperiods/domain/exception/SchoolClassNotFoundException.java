package com.educacional.schoolacademicperiods.domain.exception;

import org.springframework.http.HttpStatus;

public class SchoolClassNotFoundException extends BusinessException {
    private ExceptionCode code = ExceptionCode.SCHOOL_CLASS_NOT_FOUND;
    private static final HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;

    public SchoolClassNotFoundException(ExceptionCode code ) {
        super("School class not found", code);
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
