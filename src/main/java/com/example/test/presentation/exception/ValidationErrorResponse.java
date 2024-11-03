package com.example.test.presentation.exception;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Data
@Builder
public class ValidationErrorResponse {
    private Map<String, String> errors;
    private String message;
    private int status;
    private LocalDateTime timestamp;

    public static ValidationErrorResponse fromBindingResult(BindingResult bindingResult) {
        Map<String, String> errors = bindingResult.getFieldErrors().stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                FieldError::getDefaultMessage,
                (error1, error2) -> error1 // In case of duplicate keys
            ));

        return ValidationErrorResponse.builder()
            .errors(errors)
            .message("Validation failed")
            .status(HttpStatus.BAD_REQUEST.value())
            .timestamp(LocalDateTime.now())
            .build();
    }
} 