package com.example.mobilebankingapi.api.user.validator.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Constraint(validatedBy = PasswordMatchConstraintsValidator.class)
public @interface PasswordMatch {
    String message() default "password not match, please check again ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    //    when call this annotation we can specify password and cfpassword
    String password();

    String confirmedPassword();

    /**
     * customer password match
     */
    @Target({ElementType.TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        PasswordMatch[] value();
    }
}
