package com.educacional.schoolacademicperiods.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OrganizationNotFoundException extends BusinessException {
  private static final HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
  private final ExceptionCode code;
  private final String[] customMessageFields;

  public OrganizationNotFoundException(ExceptionCode code, String... customMessageFields) {
    super(code, customMessageFields);
    this.code = code;
    this.customMessageFields = customMessageFields;
  }
}
