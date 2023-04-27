package com.example.mobilebankingapi.api.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateUserDto(@NotBlank(message = "name is required") String name,
                            @NotBlank(message = "gender is required") String gender,
                            String studentCardId,
                            String oneSignalId,
                           @NotNull Boolean isStudent)  {
}
