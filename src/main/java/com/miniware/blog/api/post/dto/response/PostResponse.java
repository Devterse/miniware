package com.miniware.blog.api.post.dto.response;

import com.miniware.blog.api.board.dto.response.BoardResponse;
import com.miniware.blog.api.post.entity.Post;
import lombok.Getter;

@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final int viewCnt;
    private final int commentCnt;
    private final int likeCnt;
    private final BoardResponse board;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.viewCnt = post.getViewCnt();
        this.commentCnt = post.getCommentCnt();
        this.likeCnt = post.getLikeCnt();
        this.board = BoardResponse.of(post.getBoard());
    }

    public static PostResponse of(Post post) {
        return new PostResponse(post);
    }
}
