package com.miniware.blog.api.board.constant;

import com.miniware.blog.api.common.constant.CodeData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum BoardCode implements CodeData {

    BOARD_CREATED("b001",HttpStatus.CREATED, "게시판이 등록되었습니다."),
    BOARD_CREATION_FAILED("b002",HttpStatus.BAD_REQUEST, "게시판 등록이 실패하였습니다."),
    BOARD_NOT_FOUND("b003", HttpStatus.NOT_FOUND, "등록된 게시판이 없습니다."),
    BOARD_UPDATED("b004", HttpStatus.OK, "게시판이 수정되었습니다."),
    BOARD_UPDATE_FAILED("b005", HttpStatus.BAD_REQUEST, "게시판 수정에 실패하였습니다."),
    BOARD_DELETED("b006", HttpStatus.OK, "게시판이 삭제되었습니다."),
    BOARD_DELETION_FAILED("b007", HttpStatus.BAD_REQUEST, "게시판 삭제에 실패하였습니다."),
    BOARD_NAME_DUPLICATE("b008", HttpStatus.CONFLICT, "중복된 게시판 이름입니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
