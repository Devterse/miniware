package com.miniware.blog.api.auth.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RefreshRequest {
    private final Long userId;
    private final String refreshToken;

    @Builder
    public RefreshRequest(Long userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }
}
