package com.miniware.blog.api.board.dto.request;

import com.miniware.blog.api.board.entity.Board;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardCreate {

    private final String name;

    @Builder
    public BoardCreate(String name) {
        this.name = name;
    }

    public Board toEntity() {
        return Board.builder()
                .name(name)
                .build();
    }
}
