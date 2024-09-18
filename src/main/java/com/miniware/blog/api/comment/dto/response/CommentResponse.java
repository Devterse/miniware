package com.miniware.blog.api.comment.dto.response;

import com.miniware.blog.api.comment.entity.Comment;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentResponse {

    private final Long id;
    private final Long postId;
    private final Long parentId;
    private final String content;
    private final String author;
    private final String password;
    private final List<CommentResponse> replies;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getPost().getId();
        this.parentId = comment.getParent() != null ? comment.getParent().getId() : null; // null 체크
        this.content = comment.getContent();
        this.author = comment.getAuthor();
        this.password = comment.getPassword();
        this.replies = comment.getReplies().stream().map(CommentResponse::new).collect(Collectors.toList());
    }

    public static CommentResponse of(Comment comment) {
        return new CommentResponse(comment);
    }
}
