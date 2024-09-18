package com.miniware.blog.api.comment.service;

import com.miniware.blog.api.comment.dto.request.CommentCreate;
import com.miniware.blog.api.comment.dto.request.CommentEdit;
import com.miniware.blog.api.comment.dto.response.CommentResponse;
import com.miniware.blog.api.comment.entity.Comment;
import com.miniware.blog.api.comment.repository.CommentRepository;
import com.miniware.blog.api.common.exception.CustomException;
import com.miniware.blog.api.post.entity.Post;
import com.miniware.blog.api.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.miniware.blog.api.comment.constants.CommentCode.*;
import static com.miniware.blog.api.post.constant.PostCode.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommentResponse addComment(Long postId, CommentCreate request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> CustomException.of(POST_NOT_FOUND));
        Comment comment = request.toEntity(post);
        Comment response = commentRepository.save(comment);
        return CommentResponse.of(response);
    }

    @Override
    public CommentResponse addReply(Long postId, Long commentId, CommentCreate reply) {
        Comment parentComment = commentRepository.findById(commentId).orElseThrow(() -> CustomException.of(COMMENT_NOT_FOUND));
        Comment childComment = reply.toEntity(parentComment);
        Comment response = commentRepository.save(childComment);
        return CommentResponse.of(response);
    }

    @Override
    public List<CommentResponse> getComments(Long postId) {
        return Optional.ofNullable(commentRepository.findByPostId(postId))
                .filter(comments -> !comments.isEmpty()) // 리스트가 비어 있지 않은지 확인
                .map(comments -> comments.stream()
                        .filter(comment -> comment.getParent() == null)
                        .map(CommentResponse::new)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> CustomException.of(POST_NOT_FOUND));
    }

    @Override
    @Transactional
    public CommentResponse edit(Long postId, Long commentId, CommentEdit commentEdit) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> CustomException.of(COMMENT_NOT_FOUND));
        comment.edit(commentEdit);
        return CommentResponse.of(comment);
    }

    @Override
    public CommentResponse delete(Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> CustomException.of(COMMENT_NOT_FOUND));
        commentRepository.delete(comment);
        return CommentResponse.of(comment);
    }
}
