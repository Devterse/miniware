package com.miniware.blog.api.comment.dto.response;

import com.miniware.blog.api.comment.entity.Comment;
import com.miniware.blog.api.user.dto.response.UserResponse;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentResponse {

    private final Long id;
    private final Long postId;
    private final Long parentId;
    private final String content;
    private final UserResponse user;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getPost().getId();
        this.parentId = (comment.getParent() != null) ? comment.getParent().getId() : null; // null 체크
        this.content = comment.getContent();
        this.user = UserResponse.of(comment.getUser());
    }

    public static CommentResponse of(Comment comment) {
        return new CommentResponse(comment);
    }
}
