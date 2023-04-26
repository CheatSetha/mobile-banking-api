package com.example.mobilebankingapi.api.user;

public record UserDto(String name,
                      String gender,
                      String studentCardId,
                      Boolean isStudent) {
}
