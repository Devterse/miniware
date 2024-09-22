package com.miniware.blog.api.post.exception;

import com.miniware.blog.api.board.exception.BoardException;
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

    public static BoardException created() {
        return new BoardException(POST_CREATED);
    }

    public static BoardException creationFailed() {
        return new BoardException(POST_CREATION_FAILED);
    }

    public static BoardException updated() {
        return new BoardException(POST_UPDATED);
    }

    public static BoardException updatedFailed() {
        return new BoardException(POST_UPDATE_FAILED);
    }

    public static BoardException deleted() {
        return new BoardException(POST_DELETED);
    }

    public static BoardException deletionFailed() {
        return new BoardException(POST_DELETION_FAILED);
    }
}
