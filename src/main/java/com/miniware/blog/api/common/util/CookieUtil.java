package com.miniware.blog.api.common.util;

import org.springframework.http.ResponseCookie;

public class CookieUtil {

    //쿠키 생성
    public static ResponseCookie createCookie(String name, String value, long maxAge, boolean httpOnly, boolean secure) {
        return ResponseCookie.from(name, value)
                .httpOnly(httpOnly)
                .secure(secure)
                .path("/")
                .maxAge(maxAge)
                .sameSite("Strict")
                .build();
    }

    //쿠키 삭제
    public static ResponseCookie deleteCookie(String name) {
        return ResponseCookie.from(name, "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
    }
}
