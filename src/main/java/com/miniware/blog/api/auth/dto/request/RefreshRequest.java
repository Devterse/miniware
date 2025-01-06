package com.miniware.blog.api.auth.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RefreshRequest {
    private String refreshToken;

    @Builder
    public RefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
