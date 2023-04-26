package com.example.mobilebankingapi.exception;

import com.example.mobilebankingapi.base.BaseError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApiException {
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
                .message(e.getMessage())
                .error(errors)
                .build();


    }
}
