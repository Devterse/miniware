package com.miniware.blog.api.comment.service;

import com.miniware.blog.api.comment.dto.request.CommentCreate;
import com.miniware.blog.api.comment.dto.request.CommentEdit;
import com.miniware.blog.api.comment.dto.response.CommentResponse;
import com.miniware.blog.api.comment.entity.Comment;
import com.miniware.blog.api.comment.exception.CommentException;
import com.miniware.blog.api.comment.repository.CommentRepository;
import com.miniware.blog.api.post.entity.Post;
import com.miniware.blog.api.post.exception.PostException;
import com.miniware.blog.api.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public CommentResponse addComment(Long postId, CommentCreate commentCreate) {
        Post post = postRepository.findById(postId).orElseThrow(PostException::notFound);
        Comment comment = Comment.builder()
                .post(post)
                .content(commentCreate.getContent())
                .password(commentCreate.getPassword())
                .author(commentCreate.getAuthor())
                .parent(null)
                .build();
        Comment response = commentRepository.save(comment);
        return CommentResponse.of(response);
    }

    @Override
    @Transactional
    public CommentResponse addReply(Long postId, Long commentId, CommentCreate reply) {
        Post post = postRepository.findById(postId).orElseThrow(PostException::notFound);
        Comment parent = commentRepository.findById(commentId).orElseThrow(CommentException::notFound);
        Comment child = Comment.builder()
                .post(post)
                .content(reply.getContent())
                .password(reply.getPassword())
                .author(reply.getAuthor())
                .parent(parent)
                .build();
        Comment response = commentRepository.save(child);
        return CommentResponse.of(response);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long postId) {
        postRepository.findById(postId).orElseThrow(PostException::notFound);
        return Optional.of(commentRepository.findCommentsWithReplies(postId))
                .filter(comments -> !comments.isEmpty()) // 리스트가 비어 있지 않은지 확인
                .map(comments -> comments.stream()
                        .map(CommentResponse::of)
                        .collect(Collectors.toList()))
                .orElseThrow(CommentException::notFound);
    }

    @Override
    @Transactional
    public CommentResponse edit(Long postId, Long commentId, CommentEdit commentEdit) {
        postRepository.findById(postId).orElseThrow(PostException::notFound);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentException::notFound);
        comment.edit(commentEdit);
        return CommentResponse.of(comment);
    }

    @Override
    @Transactional
    public CommentResponse delete(Long postId, Long commentId) {
        postRepository.findById(postId).orElseThrow(PostException::notFound);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentException::notFound);
        commentRepository.delete(comment);
        return CommentResponse.of(comment);
    }
}
