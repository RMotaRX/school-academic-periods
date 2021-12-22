package com.educacional.schoolacademicperiods.application.rest.validator;

import lombok.Setter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Setter
public class StringEnumerationValidator implements ConstraintValidator<StringEnumeration, String> {

    private List<String> values;

    @Override
    public void initialize(StringEnumeration stringEnumeration) {
        var constants = stringEnumeration.enumClass().getEnumConstants();
        values = Arrays.stream(constants).map(Enum::name).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(
                            "Invalid ENUM value. Available values are: "
                                    + String.join(", ", values))
                    .addConstraintViolation();
            return values.contains(value);
        }
    }
}