package com.educacional.schoolacademicperiods.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DuplicateResourceException extends BusinessException {
  private final ExceptionCode code;
  private static final HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
  private final String[] customMessageFields;

  public DuplicateResourceException(ExceptionCode code, String... customMessageFields) {
    super("Resourse duplicated on database", code);
    this.code = code;
    this.customMessageFields = customMessageFields;
  }
}
