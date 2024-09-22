package com.miniware.blog.api.board.exception;

import com.miniware.blog.api.common.constant.CodeData;
import com.miniware.blog.api.common.exception.CustomException;
import static com.miniware.blog.api.board.constant.BoardCode.*;

public class BoardException extends CustomException {
    public BoardException(CodeData codeData) {
        super(codeData);
    }

    public static BoardException notFound() {
        return new BoardException(BOARD_NOT_FOUND);
    }

    public static BoardException created() {
        return new BoardException(BOARD_CREATED);
    }

    public static BoardException creationFailed() {
        return new BoardException(BOARD_CREATION_FAILED);
    }

    public static BoardException updated() {
        return new BoardException(BOARD_UPDATED);
    }

    public static BoardException updatedFailed() {
        return new BoardException(BOARD_UPDATE_FAILED);
    }

    public static BoardException deleted() {
        return new BoardException(BOARD_DELETED);
    }

    public static BoardException deletionFailed() {
        return new BoardException(BOARD_DELETION_FAILED);
    }

}
