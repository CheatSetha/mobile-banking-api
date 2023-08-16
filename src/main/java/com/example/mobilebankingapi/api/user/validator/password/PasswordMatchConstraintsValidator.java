package com.example.mobilebankingapi.api.user.validator.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordMatchConstraintsValidator implements ConstraintValidator<PasswordMatch, Object > {
    private String password;
    private String confirmedPassword;
    private String message;


    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
       this.password = constraintAnnotation.password();
       this.confirmedPassword = constraintAnnotation.confirmedPassword();
       this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
//        beanwrapperimpl use to get value from object or property
//        value is object of RegisterDto
        Object password = new BeanWrapperImpl(value).getPropertyValue(this.password);
        Object confirmedPassword = new BeanWrapperImpl(value).getPropertyValue(this.confirmedPassword);
        boolean isValid = false;
        if (password != null) {
            isValid = password.equals(confirmedPassword);
        }

//addPropertyNode() is use to show message on specific property
        if (!isValid) {
//             use to show message on specific property
            context.buildConstraintViolationWithTemplate(message)
//                    use to show message on password property
                    .addPropertyNode(this.password)
//
                    .addConstraintViolation()
//                    use to disable default message
                    .disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(this.confirmedPassword)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return isValid;
    }
}

