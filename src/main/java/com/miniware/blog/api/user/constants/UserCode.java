package com.miniware.blog.api.user.constants;

import com.miniware.blog.api.common.constant.CodeData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum UserCode implements CodeData {

    USER_RETRIEVED("u000",HttpStatus.OK,"계정이 성공적으로 조회되었습니다."),
    USER_CREATED("u001",HttpStatus.CREATED, "계정이 등록되었습니다."),
    USER_CREATION_FAILED("u002",HttpStatus.BAD_REQUEST, "계정 등록이 실패하였습니다."),
    USER_NOT_FOUND("u003", HttpStatus.NOT_FOUND, "등록된 계정이 없습니다."),
    USER_UPDATED("u004", HttpStatus.OK, "계정이 수정되었습니다."),
    USER_UPDATE_FAILED("u005", HttpStatus.BAD_REQUEST, "계정 수정에 실패하였습니다."),
    USER_DELETED("u006", HttpStatus.OK, "계정이 삭제되었습니다."),
    USER_DELETION_FAILED("u007", HttpStatus.BAD_REQUEST, "계정 삭제에 실패하였습니다."),
    USER_NAME_DUPLICATE("u008", HttpStatus.CONFLICT, "중복된 계정 입니다.");

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
