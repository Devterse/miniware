package com.devterse.miniware.api.board.service.impl;

import com.devterse.miniware.api.board.domain.BoardEntity;
import com.devterse.miniware.api.board.dto.ReqSaveBoardDto;
import com.devterse.miniware.api.board.dto.ReqUpdateBoardDto;
import com.devterse.miniware.api.board.dto.ResBoardDto;
import com.devterse.miniware.api.board.repository.BoardRepository;
import com.devterse.miniware.api.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public Long save(ReqSaveBoardDto saveBoardDto) {
        return boardRepository.save(saveBoardDto.toEntity()).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public ResBoardDto findById(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = {}" + id));
        return new ResBoardDto(boardEntity);
    }

    @Override
    @Transactional
    public Long update(Long id, ReqUpdateBoardDto dto) {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = {}" + id));
        boardEntity.update(dto);
        return boardEntity.getId();
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = {}" + id));
        boardRepository.delete(boardEntity);
        return boardEntity.getId();
    }
}
