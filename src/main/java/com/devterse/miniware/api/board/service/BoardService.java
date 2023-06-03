package com.devterse.miniware.api.board.service;
import com.devterse.miniware.api.board.dto.BoardDto;
import com.devterse.miniware.api.board.dto.ReqBoardDto.SaveBoardDto;
import com.devterse.miniware.api.board.dto.ResBoardDto;

public interface BoardService {
    public Long save(BoardDto saveBoardDto);

    public ResBoardDto findById(Long id);

    public Long update(BoardDto updateBoardDto);
}
