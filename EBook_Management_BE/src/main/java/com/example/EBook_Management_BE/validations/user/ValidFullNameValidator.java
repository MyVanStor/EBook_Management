package com.example.EBook_Management_BE.validations.user;

import com.example.EBook_Management_BE.constants.ValidPattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidFullNameValidator implements ConstraintValidator<ValidFullName, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return value.matches(ValidPattern.FULLNAME_PATTERN);
    }
}
