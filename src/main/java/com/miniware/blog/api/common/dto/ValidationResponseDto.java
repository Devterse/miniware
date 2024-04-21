package com.miniware.blog.api.common.dto;

import com.miniware.blog.api.common.constant.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ValidationResponseDto extends ResponseDto{

    private final List<FieldErrors> fieldErrors; // 여러 필드 오류를 저장할 리스트
    
    private ValidationResponseDto(BindingResult bindingResult) {
        super(false, ResponseCode.VALIDATION_ERROR.getCode(), ResponseCode.VALIDATION_ERROR.getMessage());
        this.fieldErrors = bindingResult.getFieldErrors().stream()
                .map(error -> new FieldErrors(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    public static ValidationResponseDto of(BindingResult bindingResult) {
        return new ValidationResponseDto(bindingResult);
    }


    @Getter
    @RequiredArgsConstructor
    private static class FieldErrors{
        private final String field;
        private final String message;
    }

}
