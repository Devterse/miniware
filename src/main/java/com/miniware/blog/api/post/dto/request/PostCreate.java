package com.miniware.blog.api.post.dto.request;

import com.miniware.blog.api.board.entity.Board;
import com.miniware.blog.api.post.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요.")
    private final String title;

    @NotBlank(message = "컨텐츠를 입력해주세요.")
    private final String content;

    @NotNull(message = "게시판 타입을 선택해 주세요.")
    private final Long boardId;

    @Builder
    public PostCreate(String title, String content, Long boardId) {
        this.title = title;
        this.content = content;
        this.boardId = boardId;
    }

    public Post toEntity(Board board) {
        return Post.builder()
                .title(title)
                .content(content)
                .board(board)
                .build();
    }

}
