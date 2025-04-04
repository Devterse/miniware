package com.miniware.blog.api.auth.constants;

import com.miniware.blog.api.common.constant.CodeData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AuthCode implements CodeData {

    LOGIN_SUCCESS("a000", HttpStatus.OK, "로그인이 성공적으로 완료되었습니다."),
    LOGIN_FAILURE("a001", HttpStatus.UNAUTHORIZED, "로그인에 실패하였습니다. 사용자 정보를 확인하세요."),
    LOGOUT_SUCCESS("a002", HttpStatus.OK, "로그아웃이 성공적으로 완료되었습니다."),
    LOGOUT_FAILURE("a003", HttpStatus.BAD_REQUEST, "로그아웃에 실패하였습니다."),
    ACCOUNT_LOCKED("a004", HttpStatus.UNAUTHORIZED, "계정이 잠겨 있습니다."),
    ACCOUNT_DISABLED("a005", HttpStatus.UNAUTHORIZED, "계정이 비활성화되어 있습니다."),
    CREDENTIALS_EXPIRED("a006", HttpStatus.UNAUTHORIZED, "자격 증명이 만료되었습니다."),
    REFRESH_TOKEN_INVALID("a007", HttpStatus.UNAUTHORIZED, "Refresh Token이 유효하지 않습니다."),
    ACCESS_TOKEN_SUCCESS("a008", HttpStatus.OK, "Access Token 갱신 완료되었습니다."),
    TOKEN_INVALID("a009", HttpStatus.UNAUTHORIZED, "유효하지 않은 인증 정보입니다. 다시 로그인하세요."),
    TOKEN_PARSE_ERROR("a010", HttpStatus.UNAUTHORIZED, "액세스 토큰이 유효하지 않습니다. 다시 요청하세요."),
    UNAUTHORIZED_ACCESS("a012", HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    ACCESS_DENIED("a013", HttpStatus.FORBIDDEN, "권한이 없습니다.");

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
