package com.miniware.blog.api.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

@Getter
@RequiredArgsConstructor
public class ValidationError {
    private final String field;
    private final String message;

    public static ValidationError of(FieldError fieldError) {
        return new ValidationError(fieldError.getField(), fieldError.getDefaultMessage());
    }

}