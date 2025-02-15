package com.miniware.blog.api.post.service;

import com.miniware.blog.api.post.dto.request.PostCreate;
import com.miniware.blog.api.post.dto.request.PostEdit;
import com.miniware.blog.api.post.dto.request.PostSearch;
import com.miniware.blog.api.post.dto.response.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Page<PostResponse> getList(Long boardId, PostSearch searchDto, Pageable pageable);
    PostResponse save(Long boardId, PostCreate postCreate);
    PostResponse get(Long postId);
    PostResponse edit(Long postId, PostEdit postEdit);
    void delete(Long postId);

}
