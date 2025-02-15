package com.miniware.blog.api.post.dto.response;

import com.miniware.blog.api.board.dto.response.BoardResponse;
import com.miniware.blog.api.post.entity.Post;
import com.miniware.blog.api.user.dto.response.UserResponse;
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
    private final UserResponse user;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.viewCnt = post.getViewCnt();
        this.commentCnt = post.getCommentCnt();
        this.likeCnt = post.getLikeCnt();
        this.board = BoardResponse.of(post.getBoard());
        this.user = UserResponse.of(post.getUser());
    }

    public static PostResponse of(Post post) {
        return new PostResponse(post);
    }
}
