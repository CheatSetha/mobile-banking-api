package com.example.mobilebankingapi.api.user.validator.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = PasswordConstraintsValidator.class)
public @interface Password {
    String message() default "password is not valid";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
