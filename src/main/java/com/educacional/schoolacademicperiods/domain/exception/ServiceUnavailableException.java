package com.educacional.schoolacademicperiods.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceUnavailableException extends BusinessException {

    private final ExceptionCode  code;
    private static final HttpStatus httpStatus = HttpStatus.BAD_GATEWAY;

    public ServiceUnavailableException(String message, ExceptionCode code) {
        super(message, code);
        this.code = code;
    }

    public ServiceUnavailableException(ExceptionCode code) {
        super("External Service is not available", code);
        this.code = code;
    }
}