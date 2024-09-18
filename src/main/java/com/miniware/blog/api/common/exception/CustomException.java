package com.miniware.blog.api.common.exception;

import com.miniware.blog.api.common.constant.CodeData;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private final CodeData codeData;

    public CustomException(CodeData codeData) {
        super(codeData.getMessage());
        this.codeData = codeData;
    }

    public static CustomException of(CodeData codeData) {
        return new CustomException(codeData);
    }

}
