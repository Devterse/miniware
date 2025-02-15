package com.miniware.blog.api.comment.service;

import com.miniware.blog.api.comment.dto.request.CommentCreate;
import com.miniware.blog.api.comment.dto.request.CommentEdit;
import com.miniware.blog.api.comment.dto.response.CommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    Page<CommentResponse> getParentComments(Long postId, Pageable pageable);

    Page<CommentResponse> getReplies(Long parentId, Pageable pageable);

    CommentResponse addComment(Long postId, CommentCreate comment);

    CommentResponse edit(Long postId, Long commentId, CommentEdit request);

    CommentResponse delete(Long postId, Long commentId);
}
