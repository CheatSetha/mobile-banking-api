package com.example.mobilebankingapi.api.accountType;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateAccountTypeDto(@NotBlank(message = "NAME IS REQUIRED")  String name) {
}
