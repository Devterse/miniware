package com.devterse.miniware.api.board.dto;

import com.devterse.miniware.api.board.domain.BoardEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardDto {

    private final String title;
    private final String content;
    private final String userId;

    @Builder
    public BoardDto(String title, String content, String userId) {
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
