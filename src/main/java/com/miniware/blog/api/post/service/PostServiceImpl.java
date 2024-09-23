package com.miniware.blog.api.post.service;

import com.miniware.blog.api.board.entity.Board;
import com.miniware.blog.api.board.exception.BoardException;
import com.miniware.blog.api.board.repository.BoardRepository;
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
        return PostResponse.of(post);
    }

    @Transactional
    @Override
    public PostResponse edit(Long postId, PostEdit postEdit) {
        //대부분의 수정 로직에서는 성능이 크게 중요하지 않기 때문에, 단순하게 Post와 Board를 따로 조회하는 방식이
        //오히려 더 적합할수있다. 복잡한 비즈니스 로직이 없는 경우 : Fetch join은 주로 N+1문제를 해결하기 위한 성능 최적화에 사용된다.
        //하지만 수정 작업은 일반적으로 트래픽이 많지 않으며, 데이터가 단순하기 떄문에 fetch join을 굳이 사용하지 않아도 성능 문제가 없다.
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
