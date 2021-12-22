package com.educacional.schoolacademicperiods.domain.exception;

import org.springframework.http.HttpStatus;

public class InvalidDateException extends BusinessException {
    private ExceptionCode code = ExceptionCode.INVALID_DATE;
    private static final HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
    private Object[] customMessageFields = new Object[1];

    public InvalidDateException(String message, ExceptionCode code) {
        super(message, code);
    }

    public InvalidDateException(ExceptionCode code) {
        super("The endDate is before the startDate", code);
    }

    public InvalidDateException(ExceptionCode code, String... customMessageFields) {
        super(code, customMessageFields);
        this.code = code;
        this.customMessageFields = customMessageFields;
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