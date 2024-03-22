package com.miniware.blog.api.common.dto;

import com.miniware.blog.api.common.constant.ResultResponse;
import lombok.Getter;

@Getter
public class DataResponseDto<T> extends ResponseDto{

    private final T data;

    private DataResponseDto(T data, ResultResponse resultResponse) {
        super(true, resultResponse.getCode(), resultResponse.getMessage());
        this.data = data;
    }

    private DataResponseDto(T data) {
        super(true, null, null);
        this.data = data;
    }

    public static <T> DataResponseDto<T> of(T data, ResultResponse resultResponse) {
        return new DataResponseDto<>(data, resultResponse);
    }

    public static <T> DataResponseDto<T> of(T data) {
        return new DataResponseDto<>(data);
    }
}
