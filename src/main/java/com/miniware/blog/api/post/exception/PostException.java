package com.miniware.blog.api.post.exception;

import com.miniware.blog.api.common.constant.CodeData;
import com.miniware.blog.api.common.exception.CustomException;
import static com.miniware.blog.api.post.constant.PostCode.*;

public class PostException extends CustomException {
    public PostException(CodeData codeData) {
        super(codeData);
    }

    public static PostException notFound() {
        return new PostException(POST_NOT_FOUND);
    }

    public static PostException created() {
        return new PostException(POST_CREATED);
    }

    public static PostException creationFailed() {
        return new PostException(POST_CREATION_FAILED);
    }

    public static PostException updated() {
        return new PostException(POST_UPDATED);
    }

    public static PostException updatedFailed() {
        return new PostException(POST_UPDATE_FAILED);
    }

    public static PostException deleted() {
        return new PostException(POST_DELETED);
    }

    public static PostException deletionFailed() {
        return new PostException(POST_DELETION_FAILED);
    }
}
