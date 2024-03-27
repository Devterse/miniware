package com.miniware.blog.api.common.dto;

import com.miniware.blog.api.common.constant.CodeData;
import lombok.Getter;

@Getter
public class DataResponseDto<T> extends ResponseDto{

    private final T data;

    private DataResponseDto(T data, CodeData codeData) {
        super(true, codeData.getCode(), codeData.getMessage());
        this.data = data;
    }

    private DataResponseDto(T data) {
        super(true, null, null);
        this.data = data;
    }

    public static <T> DataResponseDto<T> of(T data, CodeData codeData) {
        return new DataResponseDto<>(data, codeData);
    }

    public static <T> DataResponseDto<T> of(T data) {
        return new DataResponseDto<>(data);
    }
}
