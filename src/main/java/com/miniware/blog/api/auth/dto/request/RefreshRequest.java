package com.miniware.blog.api.auth.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RefreshRequest {
    private final String refreshToken;
    private final Long userId;

    @Builder
    public RefreshRequest(String refreshToken, Long userId) {
        this.refreshToken = refreshToken;
        this.userId = userId;
    }
}
