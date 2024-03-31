package com.miniware.blog.api.post.service;

import com.miniware.blog.api.post.dto.request.PostCreate;
import com.miniware.blog.api.post.dto.request.PostEdit;
import com.miniware.blog.api.post.dto.request.PostSearch;
import com.miniware.blog.api.post.dto.response.PostResponse;
import com.miniware.blog.api.post.entity.Post;
import com.miniware.blog.api.post.exception.PostException;
import com.miniware.blog.api.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import static com.miniware.blog.api.post.constant.PostCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    @Override
    public Page<PostResponse> getList(PostSearch searchDto, Pageable pageable) {
        return Optional.of(postRepository.getList(searchDto, pageable)
                .map(PostResponse::new))
                .filter(p -> !p.getContent().isEmpty())
                .orElseThrow(() -> PostException.of(POST_NOT_FOUND));
    }

    @Override
    public PostResponse get(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> PostException.of(POST_NOT_FOUND));
        return PostResponse.of(post);
    }

    @Override
    public PostResponse save(PostCreate postCreate) {
        Post post = postRepository.save(postCreate.toEntity());
        return Optional.of(post).map(PostResponse::new).orElseThrow(() -> PostException.of(POST_CREATION_FAILED));
    }

    @Transactional
    @Override
    public PostResponse edit(Long postId, PostEdit postEdit) {
        Post post = postRepository.findById(postId).orElseThrow(() -> PostException.of(POST_NOT_FOUND));
        post.edit(postEdit);
        return PostResponse.of(post);
    }

    @Override
    public PostResponse delete(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> PostException.of(POST_NOT_FOUND));
        postRepository.delete(post);
        return PostResponse.of(post);
    }
}
