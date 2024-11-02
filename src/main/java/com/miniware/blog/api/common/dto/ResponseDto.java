package com.miniware.blog.api.common.dto;

import com.miniware.blog.api.common.constant.CodeData;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;


@Getter
@ToString
public class ResponseDto {

    private final HttpStatus result;
    private final String code;
    private final String message;

    public ResponseDto(CodeData codeData) {
        this.result = codeData.getHttpStatus();
        this.code = codeData.getCode();
        this.message = codeData.getMessage();
    }

    public static ResponseDto of(CodeData codeData) {
        return new ResponseDto(codeData);
    }
}
