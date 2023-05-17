package com.example.mobilebankingapi.api.user;

import java.util.List;


public record UserDto(
        Integer id, String name,
        String gender,
        String studentCardId,
        Boolean isStudent
        ) {
}
