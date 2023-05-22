package com.devterse.miniware.api.board.service.impl;

import com.devterse.miniware.api.board.domain.BoardEntity;
import com.devterse.miniware.api.board.dto.ReqBoardDto;
import com.devterse.miniware.api.board.repository.BoardRepository;
import com.devterse.miniware.api.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devterse.miniware.api.board.dto.ReqBoardDto.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public Long save(SaveBoardDto saveBoardDto) {
        return boardRepository.save(saveBoardDto.toEntity()).getId();
    }
}
