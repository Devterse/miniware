package com.miniware.blog.api.post.constant;

import com.miniware.blog.api.common.constant.ResultResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PostResult implements ResultResponse {

    POST_CREATED("p001", "게시글이 등록되었습니다.", true),
    POST_CREATION_FAILED("p002", "게시글 등록이 실패하였습니다.", false),
    POST_NOT_FOUND("p003", "존재하지 않는 게시글입니다.", false),
    POST_UPDATED("p004", "게시글이 수정되었습니다.", true),
    POST_UPDATE_FAILED("p005", "게시글 수정에 실패하였습니다.", false),
    POST_DELETED("p006", "게시글이 삭제되었습니다.", true),
    POST_DELETION_FAILED("p007", "게시글 삭제에 실패하였습니다.", false);

    private final String code;
    private final String message;
    private final Boolean result;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public boolean getResult() {
        return result;
    }
}
