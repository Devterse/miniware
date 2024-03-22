package com.miniware.blog.api.common.dto;

import com.miniware.blog.api.common.constant.ResultResponse;

public class ErrorResponseDto extends ResponseDto {

    private ErrorResponseDto(ResultResponse resultResponse) {
        super(false, resultResponse.getCode(), resultResponse.getMessage());
    }

    public static ErrorResponseDto of(ResultResponse resultResponse) {
        return new ErrorResponseDto(resultResponse);
    }
}