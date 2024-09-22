package com.miniware.blog.api.board.dto.request;

import com.miniware.blog.api.board.entity.Board;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardCreate {

    private final String name;
    private final String description;

    @Builder
    public BoardCreate(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Board toEntity() {
        return Board.builder()
                .name(name)
                .description(description)
                .build();
    }
}
