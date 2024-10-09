package com.miniware.blog.api.comment.repository;

import com.miniware.blog.api.comment.entity.Comment;

import java.util.List;

public interface CommentRepositoryCustom {

    List<Comment> findCommentsWithReplies(Long postId);

}
