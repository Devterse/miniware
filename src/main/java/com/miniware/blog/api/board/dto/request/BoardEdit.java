package com.miniware.blog.api.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardEdit {

    @NotBlank(message = "게시판명을 입력해주세요.")
    private String name;

    @Builder
    public BoardEdit(String name) {
        this.name = name;
    }
}
