package com.miniware.blog.api.comment.service;

import com.miniware.blog.api.comment.dto.request.CommentCreate;
import com.miniware.blog.api.comment.dto.request.CommentEdit;
import com.miniware.blog.api.comment.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {

    CommentResponse addComment(Long postId, CommentCreate comment);

    CommentResponse addReply(Long postId, Long commentId, CommentCreate reply);

    List<CommentResponse> getComments(Long postId);

    CommentResponse edit(Long postId, Long commentId, CommentEdit request);

    CommentResponse delete(Long postId, Long commentId);
}
