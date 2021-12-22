package com.educacional.schoolacademicperiods.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResourceNotFoundException extends BusinessException {

  private final ExceptionCode code;
  private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
  private final String[] customMessageFields;

  public ResourceNotFoundException(
      String message, ExceptionCode code, String... customMessageFields) {
    super(message, code);
    this.code = code;
    this.customMessageFields = customMessageFields;
  }

  public ResourceNotFoundException(ExceptionCode code, String... customMessageFields) {
    super("Resource not found", code);
    this.code = code;
    this.customMessageFields = customMessageFields;
  }

  @Override
  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
