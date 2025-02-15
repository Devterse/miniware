package com.miniware.blog.api.comment.service;

import com.miniware.blog.api.auth.security.SecurityUtil;
import com.miniware.blog.api.comment.dto.request.CommentCreate;
import com.miniware.blog.api.comment.dto.request.CommentEdit;
import com.miniware.blog.api.comment.dto.response.CommentResponse;
import com.miniware.blog.api.comment.entity.Comment;
import com.miniware.blog.api.comment.exception.CommentException;
import com.miniware.blog.api.comment.repository.CommentRepository;
import com.miniware.blog.api.post.entity.Post;
import com.miniware.blog.api.post.exception.PostException;
import com.miniware.blog.api.post.repository.PostRepository;
import com.miniware.blog.api.user.entity.User;
import com.miniware.blog.api.user.exception.UserException;
import com.miniware.blog.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /*부모 댓글 페이징 조회*/
    @Override
    @Transactional(readOnly = true)
    public Page<CommentResponse> getParentComments(Long postId, Pageable pageable) {

        if(!postRepository.existsById(postId)) {
            throw PostException.notFound();
        }

        Page<Comment> parentComments = commentRepository.findParentComments(postId, pageable);
        return parentComments.map(CommentResponse::of);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentResponse> getReplies(Long parentId, Pageable pageable) {

        if(!commentRepository.existsById(parentId)) {
            throw CommentException.notFound();
        }

        Page<Comment> replies = commentRepository.findRepliesByParentId(parentId, pageable);
        return replies.map(CommentResponse::of);
    }

    /*댓글 등록*/
    @Override
    @Transactional
    public CommentResponse addComment(Long postId, CommentCreate request) {
        Long userId = SecurityUtil.getLoginUserId();
        User user = userRepository.findById(userId).orElseThrow(UserException::notFound);
        Post post = postRepository.findById(postId).orElseThrow(PostException::notFound);

        Comment parent = null;
        if(request.getParentId() != null) { //부모id가 있을때 대댓글
            parent = commentRepository.findById(request.getParentId()).orElseThrow(CommentException::notFound);
        }

        post.incrementCommentCount();   //댓글 수 증가

        Comment comment = Comment.builder()
                .post(post)
                .content(request.getContent())
                .password(passwordEncoder.encode(request.getPassword()))
                .user(user)
                .parent(parent)
                .build();
        Comment response = commentRepository.save(comment);
        return CommentResponse.of(response);
    }

    /*댓글 수정*/
    @Override
    @Transactional
    public CommentResponse edit(Long postId, Long commentId, CommentEdit commentEdit) {
//        postRepository.findById(postId).orElseThrow(PostException::notFound);
        Comment comment = commentRepository.findCommentById(commentId).orElseThrow(CommentException::notFound);

        if (!comment.isAuthor(SecurityUtil.getLoginUserId())) {
            throw CommentException.updateForbidden();
        }

        comment.edit(commentEdit);
        return CommentResponse.of(comment);
    }

    /*댓글 삭제*/
    @Override
    @Transactional
    public CommentResponse delete(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(PostException::notFound);
        Comment comment = commentRepository.findCommentById(commentId).orElseThrow(CommentException::notFound);

        if (!comment.isAuthor(SecurityUtil.getLoginUserId())) {
            throw CommentException.updateForbidden();
        }
        commentRepository.delete(comment);
        post.decrementCommentCount();
        return CommentResponse.of(comment);
    }
}
