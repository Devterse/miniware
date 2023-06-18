package com.devterse.miniware.api.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReqUpdateBoardDto {

    @Schema(description = "게시글 제목", nullable = false, example = "제목")
    private final String title;
    @Schema(description = "게시글 내용", nullable = false, example = "내용")
    private final String content;

    @Builder
    public ReqUpdateBoardDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
