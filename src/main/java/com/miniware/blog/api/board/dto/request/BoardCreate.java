package com.miniware.blog.api.board.dto.request;

import com.miniware.blog.api.board.entity.Board;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardCreate {

    @NotBlank(message = "이름을 입력해주세요.")
    private final String name;

    @NotBlank(message = "설명을 입력해주세요.")
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
