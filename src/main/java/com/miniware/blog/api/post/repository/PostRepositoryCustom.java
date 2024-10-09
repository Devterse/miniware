package com.miniware.blog.api.post.repository;

import com.miniware.blog.api.post.dto.request.PostSearch;
import com.miniware.blog.api.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface PostRepositoryCustom {

    Optional<Post> findPostById(Long id);

    Page<Post> getList(Long boardId, PostSearch searchDto, Pageable pageable);


}
