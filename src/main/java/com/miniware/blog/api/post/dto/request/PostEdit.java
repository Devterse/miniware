package com.miniware.blog.api.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostEdit {

    @NotBlank(message = "타이틀을 입력해주세요.")
    public String title;

    @NotBlank(message = "컨텐츠를 입력해주세요.")
    public String content;

    @NotBlank(message = "게시판 타입을 선택해 주세요.")
    private final Long boardId;

    @Builder
    public PostEdit(Long boardId, String content, String title) {
        this.boardId = boardId;
        this.content = content;
        this.title = title;
    }
}
