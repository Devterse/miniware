package com.devterse.miniware.api.board.dto;

import com.devterse.miniware.api.board.domain.BoardEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResBoardDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String userId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public ResBoardDto(BoardEntity boardEntity) {
        this.id = boardEntity.getId();
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
        this.userId = boardEntity.getUserId();
        this.createdAt = boardEntity.getCreatedAt();
        this.updatedAt = boardEntity.getUpdatedAt();
    }
}
