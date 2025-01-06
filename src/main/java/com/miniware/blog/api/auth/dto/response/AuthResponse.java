package com.miniware.blog.api.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthResponse {
    private final String accessToken;
    private final String refreshToken;

    @Builder
    public AuthResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static AuthResponse of(String accessToken, String refreshToken) {
        return new AuthResponse(accessToken, refreshToken);
    }
}
