package com.educacional.schoolacademicperiods.application.rest.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ListStringEnumerationValidator implements ConstraintValidator<ListStringEnumeration, List<String>> {

    private List<String> values;
    private StringEnumerationValidator stringEnumerationValidator;

    @Override
    public void initialize(ListStringEnumeration stringEnumeration) {
        var constants = stringEnumeration.enumClass().getEnumConstants();
        values = Arrays.stream(constants).map(Enum::name).collect(Collectors.toList());
        stringEnumerationValidator = new StringEnumerationValidator();
        stringEnumerationValidator.setValues(values);
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(
                            "Invalid ENUM value. Available values are: "
                                    + String.join(", ", values))
                    .addConstraintViolation();

            return valuesToBeValidated(value, context);
        }
    }


    boolean valuesToBeValidated(List<String> values, ConstraintValidatorContext context) {
        return values.stream().allMatch(s -> stringEnumerationValidator.isValid(s, context));
    }
}