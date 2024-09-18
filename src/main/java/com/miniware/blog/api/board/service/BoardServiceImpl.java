package com.miniware.blog.api.board.service;

import com.miniware.blog.api.board.constant.BoardCode;
import com.miniware.blog.api.board.dto.request.BoardCreate;
import com.miniware.blog.api.board.dto.request.BoardEdit;
import com.miniware.blog.api.board.dto.response.BoardResponse;
import com.miniware.blog.api.board.entity.Board;
import com.miniware.blog.api.board.repository.BoardRepository;
import com.miniware.blog.api.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public List<BoardResponse> findAll() {
        return boardRepository.findAll().stream().map(BoardResponse::of).toList();
    }

    @Override
    public BoardResponse save(BoardCreate boardCreate) {
        Board board = boardRepository.save(boardCreate.toEntity());
        return Optional.of(board).map(BoardResponse::new).orElseThrow(() -> CustomException.of(BoardCode.BOARD_CREATION_FAILED));
    }

    @Override
    public BoardResponse get(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> CustomException.of(BoardCode.BOARD_NOT_FOUND));
        return BoardResponse.of(board);
    }

    @Override
    public BoardResponse edit(Long boardId, BoardEdit boardEdit) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> CustomException.of(BoardCode.BOARD_NOT_FOUND));
        board.edit(boardEdit);
        return BoardResponse.of(board);
    }

    @Override
    public BoardResponse delete(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> CustomException.of(BoardCode.BOARD_NOT_FOUND));
        boardRepository.delete(board);
        return BoardResponse.of(board);
    }
}
