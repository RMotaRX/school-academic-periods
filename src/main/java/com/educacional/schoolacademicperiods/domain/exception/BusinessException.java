package com.educacional.schoolacademicperiods.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {
  private final ExceptionCode code;
  private static final HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
  private final String[] customMessageFields;

  public BusinessException(String message, ExceptionCode code, String... customMessageFields) {
    super(message);
    this.code = code;
    this.customMessageFields = customMessageFields;
  }

  public BusinessException(ExceptionCode code, String... customMessageFields) {
    super("Business exception");
    this.code = code;
    this.customMessageFields = customMessageFields;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public String getCodeAsString() {
    return code.toString();
  }

  public Object[] getArgs() {
    return customMessageFields;
  }
}
