package com.example.mobilebankingapi.base;

import lombok.Builder;

import java.time.LocalDateTime;
//use <T > to use generic in record
@Builder
public record BaseRest<T>(Boolean status, Integer code, String message, LocalDateTime timestamp,
                       T data) {
}
