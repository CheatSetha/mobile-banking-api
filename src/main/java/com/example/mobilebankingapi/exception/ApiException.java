package com.example.mobilebankingapi.exception;

import com.example.mobilebankingapi.base.BaseError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApiException {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseError<?> handleValidationException(MethodArgumentNotValidException e){
        List<Map<String, String >> errors = new ArrayList<>();

       for (FieldError error : e.getFieldErrors()){
           Map<String, String > errorDetails = new HashMap<>();
           errorDetails.put("name", error.getField());
           errorDetails.put("massage", error.getDefaultMessage());
           errors.add(errorDetails);
       }
        return BaseError.builder().status(false).
                code(HttpStatus.BAD_REQUEST.value()).
                timestamp(LocalDateTime.now())
                .message("Validation Error check error details ")
                .error(errors)
                .build();
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ReflectiveOperationException.class)
    public BaseError<?> handleException(ResponseStatusException e){
        return BaseError.builder().status(false).
                code(e.getStatusCode().value()).
                timestamp(LocalDateTime.now())
                .message("Internal Server Error")
                .error(e.getReason())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public BaseError<?> handleException(RuntimeException e){
        return BaseError.builder().status(false).
                code(HttpStatus.INTERNAL_SERVER_ERROR.value()).
                timestamp(LocalDateTime.now())
                .message("something went wrong..! please check and fix by yourself :(")
                .error(e.getMessage())
                .build();
    }

}
