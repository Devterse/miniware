package com.miniware.blog.api.user.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserJoin {

    private final String username;
    private final String password;

    @Builder
    public UserJoin(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
