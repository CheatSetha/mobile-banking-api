package com.example.mobilebankingapi.api.user.validator.roles;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = RoleUniqueConstaintsValidator.class)
public @interface RoleIdConstraints{
    String message() default "roles is not exist";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
