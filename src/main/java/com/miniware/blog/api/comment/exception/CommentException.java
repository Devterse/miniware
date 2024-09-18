package com.miniware.blog.api.comment.exception;

import com.miniware.blog.api.common.constant.CodeData;
import com.miniware.blog.api.common.exception.CustomException;

public class CommentException extends CustomException {
    public CommentException(CodeData codeData) {
        super(codeData);
    }

    public static CommentException of(CodeData codeData) {
        return new CommentException(codeData);
    }

}
