package com.miniware.blog.api.post.service;

import com.miniware.blog.api.board.entity.Board;
import com.miniware.blog.api.board.exception.BoardException;
import com.miniware.blog.api.board.repository.BoardRepository;
import com.miniware.blog.api.common.exception.CustomException;
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
    private final BoardRepository boardRepository;

    @Override
    public Page<PostResponse> getList(PostSearch searchDto, Pageable pageable) {
        return Optional.of(postRepository.getList(searchDto, pageable)
                .map(PostResponse::new))
                .filter(p -> !p.getContent().isEmpty())
                .orElseThrow(PostException::notFound);
    }

    @Override
    public PostResponse get(Long postId) {
        Post post = postRepository.findPostById(postId).orElseThrow(PostException::notFound);
        return PostResponse.of(post);
    }

    @Override
    public PostResponse save(PostCreate postCreate) {
        Board board = boardRepository.findById(postCreate.getBoardId()).orElseThrow(BoardException::notFound);
        Post post = postRepository.save(postCreate.toEntity(board));
        return Optional.of(post).map(PostResponse::new).orElseThrow(BoardException::creationFailed);
    }

    @Transactional
    @Override
    public PostResponse edit(Long postId, PostEdit postEdit) {
        Board board = boardRepository.findById(postEdit.getBoardId()).orElseThrow(BoardException::notFound);
        Post post = postRepository.findById(postId).orElseThrow(PostException::notFound);
        post.edit(postEdit, board);
        return PostResponse.of(post);
    }

    @Override
    public PostResponse delete(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostException::notFound);
        postRepository.delete(post);
        return PostResponse.of(post);
    }
}
