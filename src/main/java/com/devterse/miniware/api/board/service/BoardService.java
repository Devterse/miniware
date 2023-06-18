package com.devterse.miniware.api.board.service;
import com.devterse.miniware.api.board.dto.ReqSaveBoardDto;
import com.devterse.miniware.api.board.dto.ReqUpdateBoardDto;
import com.devterse.miniware.api.board.dto.ResBoardDto;

public interface BoardService {
    public Long save(ReqSaveBoardDto dto);

    public ResBoardDto findById(Long id);

    public Long update(Long id, ReqUpdateBoardDto dto);

    public Long delete(Long id);
}
