package com.miniware.blog.api.auth.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthRequest {

    private final String username;
    private final String password;

    @Builder
    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
