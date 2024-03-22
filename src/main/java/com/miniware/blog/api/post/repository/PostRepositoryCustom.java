package com.miniware.blog.api.post.repository;

import com.miniware.blog.api.post.dto.request.PostSearch;
import com.miniware.blog.api.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface PostRepositoryCustom {

    Page<Post> getList(PostSearch searchDto, Pageable pageable);

}
