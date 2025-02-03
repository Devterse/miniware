package com.miniware.blog.api.post.controller;

import com.miniware.blog.api.common.dto.PagingDto;
import com.miniware.blog.api.common.dto.ResponseDto;
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

import static com.miniware.blog.api.post.constant.PostCode.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/board/{boardId}")
    public ResponseEntity<ResponseDto<Page<PostResponse>>> getList(@PathVariable Long boardId, @ModelAttribute PostSearch searchDto, PagingDto pagingDto) {
        Pageable pageRequest = PageRequest.of(pagingDto.getPage(), pagingDto.getSize());
        Page<PostResponse> list = postService.getList(boardId, searchDto, pageRequest);
        ResponseDto<Page<PostResponse>> result = ResponseDto.of(POST_RETRIEVED,list);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ResponseDto<PostResponse>> get(@PathVariable Long postId) {
        PostResponse postResponse = postService.get(postId);
        ResponseDto<PostResponse> result = ResponseDto.of(POST_RETRIEVED, postResponse);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/board/{boardId}")
    public ResponseEntity<ResponseDto<Void>> save(@PathVariable Long boardId, @Valid @RequestBody PostCreate postCreate) {
        postService.save(boardId, postCreate);
        ResponseDto<Void> result = ResponseDto.of(POST_CREATED);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ResponseDto<Void>> update(@PathVariable Long postId, @Valid @RequestBody PostEdit postEdit) {
        postService.edit(postId, postEdit);
        ResponseDto<Void> result = ResponseDto.of(POST_UPDATED);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<ResponseDto<Void>> delete(@PathVariable Long postId) {
        postService.delete(postId);
        ResponseDto<Void> result = ResponseDto.of(POST_DELETED);
        return ResponseEntity.ok(result);
    }
}
