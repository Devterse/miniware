package com.miniware.blog.api.common.constant;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
/*ResponseCode*/
@RequiredArgsConstructor
public enum ResponseCode implements CodeData {
    //BAD_REQUEST : 잘못된 요청(400)
    //UNAUTHORIZED : 인증되지않은 사용자의 요청(401)
    //FORBIDDEN : 권한이 없는 사용자의 요청(403)
    //NOT FOUND : 리소스를 찾을 수 없음(404)
    //METHOD_NOTALLOWED : 허용되지 않은 Request Method 호출(405)
    //INTERNAL_SERVER_ERROR :  내부 서버 오류
    OK("0000", HttpStatus.OK, "ok"),
    BAD_REQUEST("1000", HttpStatus.BAD_REQUEST, "Bad request"),
    VALIDATION_ERROR("1001", HttpStatus.BAD_REQUEST, "Validation error"),
    NOT_FOUND("1002", HttpStatus.NOT_FOUND, "Not found"),
    INTERNAL_ERROR("2000", HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"),
    DATA_ACCESS_ERROR("2001", HttpStatus.INTERNAL_SERVER_ERROR, "Data access error"),
    UNAUTHORIZED("4000", HttpStatus.UNAUTHORIZED, "User unauthorized"),
    NULL_POINTER_ERROR("5000", HttpStatus.INTERNAL_SERVER_ERROR, "Null pointer exception");


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
