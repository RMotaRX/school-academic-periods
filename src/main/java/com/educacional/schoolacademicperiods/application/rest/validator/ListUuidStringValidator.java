package com.educacional.schoolacademicperiods.application.rest.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


public class ListUuidStringValidator implements ConstraintValidator<ListUuidString, List<String>> {
    String pattern = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";


    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        if (values == null) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            return valuesToBeValidated(values, context);
        }
    }

    boolean valuesToBeValidated(List<String> values, ConstraintValidatorContext context) {
        return values.stream().allMatch(s -> {
            if (!s.matches(pattern)) {
                context
                        .buildConstraintViolationWithTemplate(
                                "invalid UUID format: "
                                        + String.join(", ", s))
                        .addConstraintViolation();
            }
            return s.matches(pattern);
        });
    }
}