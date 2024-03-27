package com.miniware.blog.api.common.dto;

import com.miniware.blog.api.common.constant.CodeData;
import lombok.Getter;

@Getter
public class ErrorResponseDto extends ResponseDto {

    private String exMessage;

    private ErrorResponseDto(CodeData codeData) {
        super(false, codeData.getCode(), codeData.getMessage());
    }
    private ErrorResponseDto(CodeData codeData, RuntimeException ex) {
        super(false, codeData.getCode(), codeData.getMessage());
        this.exMessage = ex.getMessage();
    }

    public static ErrorResponseDto of(CodeData codeData) {
        return new ErrorResponseDto(codeData);
    }

    public static ErrorResponseDto of(CodeData codeData, RuntimeException ex) {
        return new ErrorResponseDto(codeData, ex);
    }
}