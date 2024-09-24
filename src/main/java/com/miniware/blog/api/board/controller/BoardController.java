package com.miniware.blog.api.board.controller;

import com.miniware.blog.api.board.dto.request.BoardCreate;
import com.miniware.blog.api.board.dto.request.BoardEdit;
import com.miniware.blog.api.board.dto.response.BoardResponse;
import com.miniware.blog.api.board.service.BoardService;
import com.miniware.blog.api.common.dto.DataResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.miniware.blog.api.board.constant.BoardCode.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<DataResponseDto<List<BoardResponse>>> getList() {
        List<BoardResponse> list = boardService.findAll();
        return ResponseEntity.ok(DataResponseDto.of(list));
    }

    /*게시판 조회*/
    @GetMapping("/{boardId}")
    public ResponseEntity<DataResponseDto<BoardResponse>> get(@PathVariable Long boardId) {
        BoardResponse boardResponse = boardService.get(boardId);
        DataResponseDto<BoardResponse> result = DataResponseDto.of(boardResponse);
        return ResponseEntity.ok(result);
    }

    /*게시판 등록*/
    @PostMapping
    public ResponseEntity<DataResponseDto<BoardResponse>> save(@RequestBody BoardCreate boardCreate) {
        BoardResponse boardResponse = boardService.save(boardCreate);
        DataResponseDto<BoardResponse> result = DataResponseDto.of(boardResponse, BOARD_CREATED);
        return ResponseEntity.ok(result);
    }

    //게시판 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<DataResponseDto<BoardResponse>> update(@PathVariable Long boardId, @RequestBody BoardEdit boardEdit) {
        BoardResponse edit = boardService.edit(boardId, boardEdit);
        DataResponseDto<BoardResponse> result = DataResponseDto.of(edit, BOARD_UPDATED);
        return ResponseEntity.ok(result);
    }

    //게시판 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<DataResponseDto<BoardResponse>> delete(@PathVariable Long boardId) {
        BoardResponse delete = boardService.delete(boardId);
        DataResponseDto<BoardResponse> result = DataResponseDto.of(delete, BOARD_DELETED);
        return ResponseEntity.ok(result);
    }
}
