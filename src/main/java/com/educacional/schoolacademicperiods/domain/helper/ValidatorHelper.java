package com.educacional.schoolacademicperiods.domain.helper;

import lombok.experimental.UtilityClass;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;

@UtilityClass
public class ValidatorHelper {

    public static <T> void validate(T domain) {
        var validates = Validation.buildDefaultValidatorFactory().getValidator().validate(domain);

        if (!validates.isEmpty()) {
            throw new ConstraintViolationException(validates);
        }
    }
}
