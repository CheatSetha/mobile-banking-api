package com.example.mobilebankingapi.exception;

import com.example.mobilebankingapi.base.BaseError;
import com.example.mobilebankingapi.base.BaseRest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class SecurityException {
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public BaseError<?> handleAuthenticationException(AuthenticationException e) {
        return BaseError.builder()
                .status(false)
                .code(HttpStatus.UNAUTHORIZED.value())
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .error(e.getMessage())
                .build();
    }
}
