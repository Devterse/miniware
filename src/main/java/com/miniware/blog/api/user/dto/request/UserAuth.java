package com.miniware.blog.api.user.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserAuth {
    private final String username;
    private final String password;

    @Builder
    public UserAuth(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
