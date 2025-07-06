package com.miniware.blog.api.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthResponse {
    private final String accessToken;

    @Builder
    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;;
    }

    public static AuthResponse of(String accessToken) {
        return new AuthResponse(accessToken);
    }

}
