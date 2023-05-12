package com.example.mobilebankingapi.api.user;

import lombok.Builder;

@Builder
public record UserDto(
                        Integer id,String name,
                      String gender,
                      String studentCardId,
                      Boolean isStudent) {
}
