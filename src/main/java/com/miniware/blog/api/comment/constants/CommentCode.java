package com.miniware.blog.api.comment.constants;

import com.miniware.blog.api.common.constant.CodeData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum CommentCode implements CodeData {

    COMMENT_CREATED("c001", HttpStatus.CREATED, "댓글이 등록되었습니다."),
    COMMENT_CREATION_FAILED("c002", HttpStatus.BAD_REQUEST, "댓글 등록이 실패하였습니다."),
    COMMENT_NOT_FOUND("c003", HttpStatus.NOT_FOUND, "등록된 댓글이 없습니다."),
    COMMENT_UPDATED("c004", HttpStatus.OK, "댓글이 수정되었습니다."),
    COMMENT_UPDATE_FAILED("c005", HttpStatus.BAD_REQUEST, "댓글 수정에 실패하였습니다."),
    COMMENT_DELETED("c006", HttpStatus.OK, "댓글 삭제되었습니다."),
    COMMENT_DELETION_FAILED("c007", HttpStatus.BAD_REQUEST, "댓글 삭제에 실패하였습니다.");

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
