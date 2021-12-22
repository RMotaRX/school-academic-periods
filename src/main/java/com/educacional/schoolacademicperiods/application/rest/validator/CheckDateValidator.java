package com.educacional.schoolacademicperiods.application.rest.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CheckDateValidator implements ConstraintValidator<DateValidator, String> {

    private String values;
    private StringEnumerationValidator stringEnumerationValidator;

    @Override
    public void initialize(DateValidator date) {

    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context
                .buildConstraintViolationWithTemplate(
                        "Invalid date format")
                .addConstraintViolation();
        if (date == null) {
            return true;
        }
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
