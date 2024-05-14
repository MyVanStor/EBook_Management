package com.example.EBook_Management_BE.validations.user;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.EBook_Management_BE.utils.MessageKeyValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidFullNameValidator.class)
public @interface ValidFullName { 
	String message() default MessageKeyValidation.USER_FULL_NAME;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
