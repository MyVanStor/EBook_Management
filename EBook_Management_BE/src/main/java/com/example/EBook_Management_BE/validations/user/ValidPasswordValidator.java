package com.example.EBook_Management_BE.validations.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String>  {
	private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[!@#$%^&*()-_=+{};:,<.>])(?=.{6,20}$).+$";
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
            return true;
        }
        return value.matches(PASSWORD_PATTERN);
	}
	
}
