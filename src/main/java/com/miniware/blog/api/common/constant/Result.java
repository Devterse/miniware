package com.miniware.blog.api.common.constant;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@RequiredArgsConstructor
public enum Result implements ResultResponse {
    OK("0", HttpStatus.OK, "ok"),
    BAD_REQUEST("10000", HttpStatus.BAD_REQUEST, "Bad request"),
    VALIDATION_ERROR("10001", HttpStatus.BAD_REQUEST, "Validation error"),
    NOT_FOUND("10002", HttpStatus.NOT_FOUND, "Not found"),
    INTERNAL_ERROR("20000", HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"),
    DATA_ACCESS_ERROR("20001", HttpStatus.INTERNAL_SERVER_ERROR, "Data access error"),
    UNAUTHORIZED("40000", HttpStatus.UNAUTHORIZED, "User unauthorized");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;


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
        return true;
    }
}
