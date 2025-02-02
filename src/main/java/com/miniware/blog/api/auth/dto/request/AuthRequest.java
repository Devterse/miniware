package com.miniware.blog.api.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthRequest {

    @NotBlank(message = "username을 입력해주세요.")
    private final String username;

    @NotBlank(message = "패스워드를 입력해주세요.")
    private final String password;

    @Builder
    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
