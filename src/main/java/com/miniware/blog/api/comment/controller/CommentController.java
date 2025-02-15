package com.miniware.blog.api.comment.controller;

import com.miniware.blog.api.comment.dto.request.CommentCreate;
import com.miniware.blog.api.comment.dto.request.CommentEdit;
import com.miniware.blog.api.comment.dto.response.CommentResponse;
import com.miniware.blog.api.comment.service.CommentService;
import com.miniware.blog.api.common.dto.PagingDto;
import com.miniware.blog.api.common.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.miniware.blog.api.comment.constants.CommentCode.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    /*부모 댓글 조회*/
    @GetMapping
    public ResponseEntity<ResponseDto<Page<CommentResponse>>> getParentComments(@PathVariable Long postId, PagingDto pagingDto) {
        Pageable pageable = pagingDto.toPageable();
        Page<CommentResponse> parentComments = commentService.getParentComments(postId, pageable);
        ResponseDto<Page<CommentResponse>> result = ResponseDto.of(COMMENT_RETRIEVED, parentComments);
        return ResponseEntity.ok(result);
    }

    /*부모 댓글의 대댓글*/
    @GetMapping("/{commentId}/replies")
    public ResponseEntity<ResponseDto<Page<CommentResponse>>> getReplies(@PathVariable Long postId, @PathVariable Long commentId, PagingDto pagingDto) {
        Pageable pageable = pagingDto.toPageable();
        Page<CommentResponse> replies = commentService.getReplies(commentId, pageable);
        ResponseDto<Page<CommentResponse>> result = ResponseDto.of(COMMENT_RETRIEVED, replies);
        return ResponseEntity.ok(result);
    }

    //댓글 등록
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> addComment(@PathVariable Long postId, @RequestBody @Valid CommentCreate request) {
        commentService.addComment(postId, request);
        ResponseDto<Void> result = ResponseDto.of(COMMENT_CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    //댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseDto<Void>> update(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody @Valid CommentEdit commentEdit) {
        commentService.edit(postId, commentId, commentEdit);
        ResponseDto<Void> result = ResponseDto.of(COMMENT_UPDATED);
        return ResponseEntity.ok(result);
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseDto<Void>> delete(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.delete(postId, commentId);
        ResponseDto<Void> result = ResponseDto.of(COMMENT_DELETED);
        return ResponseEntity.ok(result);
    }
}