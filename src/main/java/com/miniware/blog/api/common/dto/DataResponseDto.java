package com.miniware.blog.api.common.dto;

import com.miniware.blog.api.common.constant.CodeData;
import lombok.Getter;

@Getter
public class DataResponseDto<T> extends ResponseDto{

    private final T data;

    private DataResponseDto(T data, CodeData codeData) {
        super(codeData);
        this.data = data;
    }

    public static <T> DataResponseDto<T> of(T data, CodeData codeData) {
        return new DataResponseDto<>(data, codeData);
    }
}
