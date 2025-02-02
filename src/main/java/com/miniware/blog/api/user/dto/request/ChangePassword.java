package com.miniware.blog.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChangePassword {

    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    private final String currentPassword;

    @NotBlank(message = "새 비밀번호를 입력해주세요.")
    private final String newPassword;

    @Builder
    public ChangePassword(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }
}
