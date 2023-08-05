package com.example.mobilebankingapi.api.auth.wep;

import com.example.mobilebankingapi.api.user.validator.password.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LogInDto(
        @Email
        @NotBlank String email,
        @NotBlank
        @Password String password) {
}
