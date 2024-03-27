package com.miniware.blog.api.post.exception;

import com.miniware.blog.api.common.constant.CodeData;
import com.miniware.blog.api.common.exception.CustomException;

public class PostException extends CustomException {
    public PostException(CodeData errorData) {
        super(errorData);
    }

    public static PostException of(CodeData errorData) {
        return new PostException(errorData);
    }

}
