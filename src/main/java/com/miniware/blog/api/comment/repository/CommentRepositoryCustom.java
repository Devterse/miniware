package com.miniware.blog.api.comment.repository;

import com.miniware.blog.api.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryCustom {

    Page<Comment> findParentComments(Long postId, Pageable pageable);

    Page<Comment> findRepliesByParentId(Long parentId, Pageable pageable);

    Optional<Comment> findCommentById(Long id);

}
