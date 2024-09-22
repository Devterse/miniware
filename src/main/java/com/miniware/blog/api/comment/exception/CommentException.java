package com.miniware.blog.api.comment.exception;

import com.miniware.blog.api.common.constant.CodeData;
import com.miniware.blog.api.common.exception.CustomException;
import static com.miniware.blog.api.comment.constants.CommentCode.*;

public class CommentException extends CustomException {
    public CommentException(CodeData codeData) {
        super(codeData);
    }

    public static CommentException notFound() {
        return new CommentException(COMMENT_NOT_FOUND);
    }

    public static CommentException created() {
        return new CommentException(COMMENT_CREATED);
    }

    public static CommentException creationFailed() {
        return new CommentException(COMMENT_CREATION_FAILED);
    }

    public static CommentException updated() {
        return new CommentException(COMMENT_UPDATED);
    }

    public static CommentException updatedFailed() {
        return new CommentException(COMMENT_UPDATE_FAILED);
    }

    public static CommentException deleted() {
        return new CommentException(COMMENT_DELETED);
    }

    public static CommentException deletionFailed() {
        return new CommentException(COMMENT_DELETION_FAILED);
    }

}
