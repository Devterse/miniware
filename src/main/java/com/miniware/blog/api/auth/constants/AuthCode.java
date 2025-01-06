package com.miniware.blog.api.auth.constants;

import com.miniware.blog.api.common.constant.CodeData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AuthCode implements CodeData {

    SIGN_UP_SUCCESS("a000", HttpStatus.CREATED, "회원가입이 성공적으로 완료되었습니다."),
    SIGN_UP_FAILURE("a001", HttpStatus.BAD_REQUEST, "회원가입에 실패하였습니다."),
    LOGIN_SUCCESS("a002", HttpStatus.OK, "로그인이 성공적으로 완료되었습니다."),
    LOGIN_FAILURE("a003", HttpStatus.UNAUTHORIZED, "로그인에 실패하였습니다. 사용자 정보를 확인하세요."),
    LOGOUT_SUCCESS("a004", HttpStatus.OK, "로그아웃이 성공적으로 완료되었습니다."),
    LOGOUT_FAILURE("a005", HttpStatus.BAD_REQUEST, "로그아웃에 실패하였습니다."),
    ACCOUNT_LOCKED("a006", HttpStatus.UNAUTHORIZED, "계정이 잠겨 있습니다."),
    ACCOUNT_DISABLED("a007", HttpStatus.UNAUTHORIZED, "계정이 비활성화되어 있습니다."),
    CREDENTIALS_EXPIRED("a008", HttpStatus.UNAUTHORIZED, "자격 증명이 만료되었습니다.");

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
