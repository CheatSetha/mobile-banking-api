package com.example.mobilebankingapi.api.user.validator.email;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = EmailUniqueConstraintsValidator.class)
public @interface EmailUnique {
    String message() default "email is already exist";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
