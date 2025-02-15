package com.miniware.blog.api.post.service;

import com.miniware.blog.api.auth.security.SecurityUtil;
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
import com.miniware.blog.api.user.entity.User;
import com.miniware.blog.api.user.exception.UserException;
import com.miniware.blog.api.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponse> getList(Long boardId, PostSearch searchDto, Pageable pageable) {
        return Optional.of(postRepository.getList(boardId, searchDto, pageable)
                        .map(PostResponse::new))
                .filter(p -> !p.getContent().isEmpty())
                .orElseThrow(PostException::notFound);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse get(Long postId) {
        Post post = postRepository.findPostById(postId).orElseThrow(PostException::notFound);
        post.incrementViewCount();  //조회수 증가
        return PostResponse.of(post);
    }

    @Override
    @Transactional
    public PostResponse save(Long boardId, PostCreate postCreate) {
        Long userId = SecurityUtil.getLoginUserId();
        User user = userRepository.findById(userId).orElseThrow(UserException::notFound);
        Board board = boardRepository.findById(boardId).orElseThrow(BoardException::notFound);

        Post post = postCreate.toEntity(user);
        board.addPost(post);
        postRepository.save(post);
        return PostResponse.of(post);
    }

    @Override
    @Transactional
    public PostResponse edit(Long postId, PostEdit postEdit) {
        //대부분의 수정 로직에서는 성능이 크게 중요하지 않기 때문에, 단순하게 Post와 Board를 따로 조회하는 방식이
        //오히려 더 적합할수있다. 복잡한 비즈니스 로직이 없는 경우 : Fetch join은 주로 N+1문제를 해결하기 위한 성능 최적화에 사용된다.
        //하지만 수정 작업은 일반적으로 트래픽이 많지 않으며, 데이터가 단순하기 떄문에 fetch join을 굳이 사용하지 않아도 성능 문제가 없다.
        Board board = boardRepository.findById(postEdit.getBoardId()).orElseThrow(BoardException::notFound);
        Post post = postRepository.findPostById(postId).orElseThrow(PostException::notFound);

        //작성자 확인
        if (!post.isAuthor(SecurityUtil.getLoginUserId())) {
            throw PostException.updateForbidden();
        }
        //기존 게시판과 다른 경우, 양방향 관계 처리
        if(!post.getBoard().equals(board)) {
            // 이전 게시판의 게시글 목록에서 제거
            Board oldBoard = post.getBoard();
            oldBoard.removePost(post);

            // 새로운 게시판의 게시글 목록에 추가
            board.addPost(post);
        }

        post.edit(postEdit, board);
        return PostResponse.of(post);
    }

    @Override
    @Transactional
    public void delete(Long postId) {
        Post post = postRepository.findPostById(postId).orElseThrow(PostException::notFound);

        //작성자 확인
        if (!post.isAuthor(SecurityUtil.getLoginUserId())) {
            throw PostException.deleteForbidden();
        }

        Board board = post.getBoard();  //게시판에서 게시글 제거(양방향 관계 해제)

        if (board != null) {
            board.removePost(post); // 게시판과의 관계 해제
        }

        postRepository.delete(post);
    }
}
