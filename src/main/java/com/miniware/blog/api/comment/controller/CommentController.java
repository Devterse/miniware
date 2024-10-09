package com.miniware.blog.api.comment.controller;

import com.miniware.blog.api.comment.dto.request.CommentCreate;
import com.miniware.blog.api.comment.dto.request.CommentEdit;
import com.miniware.blog.api.comment.dto.response.CommentResponse;
import com.miniware.blog.api.comment.service.CommentService;
import com.miniware.blog.api.common.dto.DataResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.miniware.blog.api.comment.constants.CommentCode.*;
import static com.miniware.blog.api.common.constant.ResponseCode.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    //댓글 등록
    @PostMapping
    public ResponseEntity<DataResponseDto<CommentResponse>> addComment(@PathVariable Long postId, @RequestBody @Valid CommentCreate comment) {
        CommentResponse commentResponse = commentService.addComment(postId, comment);
        DataResponseDto<CommentResponse> result = DataResponseDto.of(commentResponse, COMMENT_CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    //대댓글 등록
    @PostMapping("/{commentId}")
    public ResponseEntity<DataResponseDto<CommentResponse>> addReply(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody @Valid CommentCreate reply) {
        CommentResponse commentResponse = commentService.addReply(postId, commentId, reply);
        DataResponseDto<CommentResponse> result = DataResponseDto.of(commentResponse, COMMENT_CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    //댓글 목록
    @GetMapping
    public ResponseEntity<DataResponseDto<List<CommentResponse>>> getComments(@PathVariable Long postId) {
        List<CommentResponse> comments = commentService.getComments(postId);
        DataResponseDto<List<CommentResponse>> result = DataResponseDto.of(comments, OK);
        return ResponseEntity.ok(result);
    }

    //댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<DataResponseDto<CommentResponse>> update(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody @Valid CommentEdit commentEdit) {
        CommentResponse commentResponse = commentService.edit(postId, commentId, commentEdit);
        DataResponseDto<CommentResponse> result = DataResponseDto.of(commentResponse, COMMENT_UPDATED);
        return ResponseEntity.ok(result);
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<DataResponseDto<CommentResponse>> delete(@PathVariable Long postId, @PathVariable Long commentId) {
        CommentResponse delete = commentService.delete(postId, commentId);
        DataResponseDto<CommentResponse> result = DataResponseDto.of(delete, COMMENT_DELETED);
        return ResponseEntity.ok(result);
    }
}