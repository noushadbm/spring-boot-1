package com.rayshan.gitinfo.validation;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class EnumValueValidator implements ConstraintValidator<ValidEnumValue, String> {
    private List<String> validValues = null;

    @Override
    public void initialize(ValidEnumValue constraintAnnotation) {
        validValues = new ArrayList<>();
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();
        Enum[] constantsArray = enumClass.getEnumConstants();
        for (Enum constant : constantsArray) {
            validValues.add(constant.toString());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value) return true;

        context.disableDefaultConstraintViolation();

        // Prepare error message
        String allValidValues = StringUtils.join(validValues, ",");
        context.buildConstraintViolationWithTemplate(
                        String.format("Should be any of %s", allValidValues))
                .addConstraintViolation();

        return validValues.contains(value);
    }
}
