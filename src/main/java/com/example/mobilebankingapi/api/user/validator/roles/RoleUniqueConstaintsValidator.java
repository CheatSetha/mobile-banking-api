package com.example.mobilebankingapi.api.user.validator.roles;

import com.example.mobilebankingapi.api.user.UserMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class RoleUniqueConstaintsValidator implements ConstraintValidator<RoleIdConstraints, List<Integer> >{
    private final UserMapper userMapper;
    @Override
    public boolean isValid(List<Integer> roleIds, ConstraintValidatorContext context) {
        for (Integer rolesId: roleIds) {
            if (!userMapper.checkRoleId(rolesId))
                return false;
        }
        return true;
    }
}
