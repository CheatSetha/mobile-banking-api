package com.example.mobilebankingapi.api.auth.wep;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RegisterDto(
        @NotBlank(message = "email required") @Email String email,
        @NotBlank(message = "password required") String password,
        @NotBlank(message = "confirm password required") String confirmedPassword ,
        @NotNull(message = "role required") List<Integer> roleIds
        ) {
}
