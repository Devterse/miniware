package com.miniware.blog.api.post.constant;

import com.miniware.blog.api.common.constant.CodeData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum PostCode implements CodeData {

    POST_CREATED("p001",HttpStatus.CREATED, "게시글이 등록되었습니다."),
    POST_CREATION_FAILED("p002",HttpStatus.BAD_REQUEST, "게시글 등록이 실패하였습니다."),
    POST_NOT_FOUND("p003", HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
    POST_UPDATED("p004", HttpStatus.OK, "게시글이 수정되었습니다."),
    POST_UPDATE_FAILED("p005", HttpStatus.BAD_REQUEST, "게시글 수정에 실패하였습니다."),
    POST_DELETED("p006", HttpStatus.OK, "게시글이 삭제되었습니다."),
    POST_DELETION_FAILED("p007", HttpStatus.BAD_REQUEST, "게시글 삭제에 실패하였습니다.");

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
