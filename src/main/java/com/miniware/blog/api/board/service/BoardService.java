package com.miniware.blog.api.board.service;

import com.miniware.blog.api.board.dto.request.BoardCreate;
import com.miniware.blog.api.board.dto.request.BoardEdit;
import com.miniware.blog.api.board.dto.response.BoardResponse;

import java.util.List;

public interface BoardService {

    List<BoardResponse> findAll();
    BoardResponse get(Long boardId);
    BoardResponse save(BoardCreate boardCreate);
    BoardResponse edit(Long boardId, BoardEdit boardEdit);
    BoardResponse delete(Long boardId);
}
