package com.devterse.miniware.api.board.service.impl;

import com.devterse.miniware.api.board.domain.BoardEntity;
import com.devterse.miniware.api.board.dto.BoardDto;
import com.devterse.miniware.api.board.dto.ResBoardDto;
import com.devterse.miniware.api.board.repository.BoardRepository;
import com.devterse.miniware.api.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public Long save(BoardDto saveBoardDto) {
        return boardRepository.save(saveBoardDto.toEntity()).getId();
    }

    @Override
    public ResBoardDto findById(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = {}" + id));
        return new ResBoardDto(boardEntity);
    }
}
