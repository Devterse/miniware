package com.miniware.blog.api.auth.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RefreshRequest {
    private final String refreshToken;
    private String userId;

    @Builder
    public RefreshRequest(String refreshToken, String userId) {
        this.refreshToken = refreshToken;
        this.userId = userId;
    }
}
