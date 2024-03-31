package com.miniware.blog.api.post.exception;

import com.miniware.blog.api.common.constant.CodeData;
import com.miniware.blog.api.common.exception.CustomException;

public class PostException extends CustomException {
    public PostException(CodeData codeData) {
        super(codeData);
    }

    public static PostException of(CodeData codeData) {
        return new PostException(codeData);
    }

}
