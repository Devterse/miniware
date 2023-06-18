package com.devterse.miniware.api.board.dto;

import com.devterse.miniware.api.board.domain.BoardEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "게시판 등록 요청 DTO")
public class ReqSaveBoardDto {

    @Schema(description = "게시글 제목", nullable = false, example = "제목")
    private final String title;
    @Schema(description = "게시글 내용", nullable = false, example = "내용")
    private final String content;
    @Schema(description = "게시글 작성자", nullable = false, example = "작성자")
    private final String userId;

    @Builder
    public ReqSaveBoardDto(String title, String content, String userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    /*DTO To Entity*/
    public BoardEntity toEntity() {
        return BoardEntity.builder()
            .title(this.title)
            .content(this.content)
            .userId(this.userId)
            .build();
    }
}
