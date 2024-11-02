package com.miniware.blog.api.common.dto;

import com.miniware.blog.api.common.constant.CodeData;
import com.miniware.blog.api.common.constant.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ValidationResponseDto extends ResponseDto{

    private final List<ValidationError> fieldErrors; // 여러 필드 오류를 저장할 리스트

    private ValidationResponseDto(BindingResult bindingResult) {
        super(ResponseCode.VALIDATION_ERROR);
        this.fieldErrors = bindingResult.getFieldErrors().stream()
                .map(error -> new ValidationError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    public static ValidationResponseDto of(BindingResult bindingResult) {
        return new ValidationResponseDto(bindingResult);
    }

}
