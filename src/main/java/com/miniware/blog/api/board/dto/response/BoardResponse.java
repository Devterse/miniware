package com.miniware.blog.api.board.dto.response;

import com.miniware.blog.api.board.entity.Board;
import lombok.Getter;

@Getter
public class BoardResponse {

    private final Long id;
    private final String name;
    private final String description;

    public BoardResponse(Board board) {
        this.id = board.getId();
        this.name = board.getName();
        this.description = board.getDescription();
    }

    public static BoardResponse of(Board board) {
        return new BoardResponse(board);
    }

}
