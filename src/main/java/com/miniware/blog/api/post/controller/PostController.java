package com.miniware.blog.api.post.controller;

import com.miniware.blog.api.common.dto.DataResponseDto;
import com.miniware.blog.api.common.dto.PagingDto;
import com.miniware.blog.api.post.dto.request.PostCreate;
import com.miniware.blog.api.post.dto.request.PostEdit;
import com.miniware.blog.api.post.dto.request.PostSearch;
import com.miniware.blog.api.post.dto.response.PostResponse;
import com.miniware.blog.api.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.miniware.blog.api.common.constant.ResponseCode.*;
import static com.miniware.blog.api.post.constant.PostCode.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/board/{boardId}")
    public ResponseEntity<DataResponseDto<Page<PostResponse>>> getList(
            @PathVariable Long boardId,
            @ModelAttribute PostSearch searchDto,
            PagingDto pagingDto) {
        Pageable pageRequest = PageRequest.of(pagingDto.getPage(), pagingDto.getSize());
        Page<PostResponse> list = postService.getList(boardId, searchDto, pageRequest);
        DataResponseDto<Page<PostResponse>> result = DataResponseDto.of(list);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<DataResponseDto<PostResponse>> get(@PathVariable Long postId) {
        PostResponse postResponse = postService.get(postId);
        DataResponseDto<PostResponse> result = DataResponseDto.of(postResponse, OK);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/board/{boardId}")
    public ResponseEntity<DataResponseDto<PostResponse>> save(@PathVariable Long boardId, @Valid @RequestBody PostCreate request) {
        PostResponse postResponse = postService.save(boardId, request);
        DataResponseDto<PostResponse> result = DataResponseDto.of(postResponse, POST_CREATED);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<DataResponseDto<PostResponse>> update(@PathVariable Long postId, @Valid @RequestBody PostEdit postEdit) {
        PostResponse postResponse = postService.edit(postId, postEdit);
        DataResponseDto<PostResponse> result = DataResponseDto.of(postResponse, POST_UPDATED);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<DataResponseDto<PostResponse>> delete(@PathVariable Long postId) {
        PostResponse postResponse = postService.delete(postId);
        DataResponseDto<PostResponse> result = DataResponseDto.of(postResponse, POST_DELETED);
        return ResponseEntity.ok(result);
    }
}
