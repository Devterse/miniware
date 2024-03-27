package com.miniware.blog.api.common.exception;

import com.miniware.blog.api.common.constant.CodeData;
import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException{

    private final CodeData codeData;

    protected CustomException(CodeData codeData) {
        super(codeData.getMessage());
        this.codeData = codeData;
    }
}
