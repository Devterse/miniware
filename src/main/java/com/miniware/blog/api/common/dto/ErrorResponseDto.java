package com.miniware.blog.api.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.miniware.blog.api.common.constant.CodeData;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponseDto extends ResponseDto {

    private String exMessage;

    private ErrorResponseDto(CodeData codeData) {
        super(false, codeData.getCode(), codeData.getMessage());
    }

    private ErrorResponseDto(CodeData codeData, Exception ex) {
        super(false, codeData.getCode(), codeData.getMessage());
        this.exMessage = ex.getMessage();
    }

    public static ErrorResponseDto of(CodeData codeData) {
        return new ErrorResponseDto(codeData);
    }

    public static ErrorResponseDto of(CodeData codeData, Exception ex) {
        return new ErrorResponseDto(codeData, ex);
    }
}