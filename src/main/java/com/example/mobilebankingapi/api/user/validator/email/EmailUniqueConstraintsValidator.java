package com.example.mobilebankingapi.api.user.validator.email;

import com.example.mobilebankingapi.api.user.UserMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

//    inorder to use the emailUnique annotation we need to implement the constraint validator interface
//    and we need to pass the annotation and the type of the field that we want to validate
@RequiredArgsConstructor
public class EmailUniqueConstraintsValidator implements ConstraintValidator<EmailUnique, String> {
    private final UserMapper userMapper;



    /**
     * Implements the validation logic.
     * @param email object to validate
     * @param context context in which the constraint is evaluated
     *
     * @return
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !userMapper.isUserExistByEmail(email);
    }

}
