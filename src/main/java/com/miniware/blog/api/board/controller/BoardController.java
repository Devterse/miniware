package com.miniware.blog.api.board.controller;

import com.miniware.blog.api.board.dto.request.BoardCreate;
import com.miniware.blog.api.board.dto.request.BoardEdit;
import com.miniware.blog.api.board.dto.response.BoardResponse;
import com.miniware.blog.api.board.service.BoardService;
import com.miniware.blog.api.common.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseDto<List<BoardResponse>>> getList() {
        List<BoardResponse> list = boardService.findAll();
        return ResponseEntity.ok(ResponseDto.of(BOARD_RETRIEVED, list));
    }

    /*게시판 조회*/
    @GetMapping("/{boardId}")
    public ResponseEntity<ResponseDto<BoardResponse>> get(@PathVariable Long boardId) {
        BoardResponse boardResponse = boardService.get(boardId);
        ResponseDto<BoardResponse> result = ResponseDto.of(BOARD_RETRIEVED, boardResponse);
        return ResponseEntity.ok(result);
    }

    /*게시판 등록*/
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> save(@Valid @RequestBody BoardCreate boardCreate) {
        boardService.save(boardCreate);
        ResponseDto<Void> result = ResponseDto.of(BOARD_CREATED);
        return ResponseEntity.ok(result);
    }

    //게시판 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<ResponseDto<Void>> update(@PathVariable Long boardId, @Valid @RequestBody BoardEdit boardEdit) {
        boardService.edit(boardId, boardEdit);
        ResponseDto<Void> result = ResponseDto.of(BOARD_UPDATED);
        return ResponseEntity.ok(result);
    }

    //게시판 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<ResponseDto<Void>> delete(@PathVariable Long boardId) {
        boardService.delete(boardId);
        ResponseDto<Void> result = ResponseDto.of(BOARD_DELETED);
        return ResponseEntity.ok(result);
    }
}
