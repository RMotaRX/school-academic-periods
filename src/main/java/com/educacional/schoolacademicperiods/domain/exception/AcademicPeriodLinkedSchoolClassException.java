package com.educacional.schoolacademicperiods.domain.exception;

import org.springframework.http.HttpStatus;

public class AcademicPeriodLinkedSchoolClassException extends BusinessException {

    private ExceptionCode code = ExceptionCode.ACADEMIC_PERIOD_LINKED_WITH_SCHOOL_CLASS;
    private static final HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;

    public AcademicPeriodLinkedSchoolClassException(ExceptionCode code ) {
        super("This Academic Period have School Class linked", code);
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
