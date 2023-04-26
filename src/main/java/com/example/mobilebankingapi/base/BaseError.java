package com.example.mobilebankingapi.base;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@Builder
@ResponseStatus(HttpStatus.BAD_REQUEST)
public record BaseError<T>(Boolean status, Integer code, String message, LocalDateTime timestamp,
                           T error) {
}
