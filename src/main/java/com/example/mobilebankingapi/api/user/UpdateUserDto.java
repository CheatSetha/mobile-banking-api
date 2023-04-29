package com.example.mobilebankingapi.api.user;

import lombok.Builder;

@Builder
public record  UpdateUserDto(String name , String gender) {
}
