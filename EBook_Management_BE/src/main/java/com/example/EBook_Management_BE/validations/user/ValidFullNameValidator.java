package com.example.EBook_Management_BE.validations.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidFullNameValidator implements ConstraintValidator<ValidFullName, String> {
	private static final String FULLNAME_PATTERN = "^[a-zA-Z\\s]+$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.matches(FULLNAME_PATTERN);
    }
}
