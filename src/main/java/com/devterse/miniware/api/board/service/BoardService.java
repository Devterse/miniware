package com.devterse.miniware.api.board.service;
import com.devterse.miniware.api.board.dto.ReqBoardDto.SaveBoardDto;

public interface BoardService {
    public Long save(SaveBoardDto saveBoardDto);
}
