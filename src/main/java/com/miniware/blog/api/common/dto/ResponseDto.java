package com.miniware.blog.api.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@Getter
@ToString
@RequiredArgsConstructor
public class ResponseDto {

    private final Boolean result;
    private final String code;
    private final String message;

    public static ResponseDto of(Boolean result, String code, String message) {
        return new ResponseDto(result, code, message);
    }
}
