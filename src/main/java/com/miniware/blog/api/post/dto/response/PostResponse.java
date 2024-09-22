package com.miniware.blog.api.post.dto.response;

import com.miniware.blog.api.board.dto.response.BoardResponse;
import com.miniware.blog.api.post.entity.Post;
import lombok.Getter;

@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final BoardResponse board;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.board = BoardResponse.of(post.getBoard());
    }

    public static PostResponse of(Post post) {
        return new PostResponse(post);
    }
}
