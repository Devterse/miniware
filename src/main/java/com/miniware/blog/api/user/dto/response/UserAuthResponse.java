package com.miniware.blog.api.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserAuthResponse {
    private final String accessToken;
    private final String refreshToken;

    @Builder
    public UserAuthResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
