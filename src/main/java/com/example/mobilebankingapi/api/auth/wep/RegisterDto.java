package com.example.mobilebankingapi.api.auth.wep;

import com.example.mobilebankingapi.api.user.validator.email.EmailUnique;
import com.example.mobilebankingapi.api.user.validator.password.Password;
import com.example.mobilebankingapi.api.user.validator.roles.RoleIdConstraints;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RegisterDto(
        @NotBlank(message = "email required")
        @Email
        @EmailUnique String email,
        @NotBlank(message = "password required")
        @Password String password,
        @NotBlank(message = "confirm password required")
        @Password String confirmedPassword,
        @NotNull(message = "role required")
        @RoleIdConstraints List<Integer> roleIds
) {
}
