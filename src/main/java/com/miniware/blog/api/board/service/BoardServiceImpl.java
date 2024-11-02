package com.miniware.blog.api.board.service;

import com.miniware.blog.api.board.constant.BoardCode;
import com.miniware.blog.api.board.dto.request.BoardCreate;
import com.miniware.blog.api.board.dto.request.BoardEdit;
import com.miniware.blog.api.board.dto.response.BoardResponse;
import com.miniware.blog.api.board.entity.Board;
import com.miniware.blog.api.board.exception.BoardException;
import com.miniware.blog.api.board.repository.BoardRepository;
import com.miniware.blog.api.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BoardResponse> findAll() {
        return boardRepository.findAll().stream().map(BoardResponse::new).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BoardResponse get(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardException::notFound);
        return BoardResponse.of(board);
    }

    @Override
    @Transactional
    public BoardResponse save(BoardCreate boardCreate) {
        if(boardRepository.existsByName(boardCreate.getName())) {
            throw BoardException.duplicate();
        }
        Board board = boardRepository.save(boardCreate.toEntity());
        return BoardResponse.of(board);
    }

    @Override
    @Transactional
    public BoardResponse edit(Long boardId, BoardEdit boardEdit) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardException::notFound);

        if (boardRepository.existsByNameAndIdNot(boardEdit.getName(), boardId)) {
            throw BoardException.duplicate();
        }

        board.edit(boardEdit);
        return BoardResponse.of(board);
    }

    @Override
    @Transactional
    public BoardResponse delete(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardException::notFound);
        boardRepository.delete(board);
        return BoardResponse.of(board);
    }
}
