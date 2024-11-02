package com.miniware.blog.api.board.controller;

import com.miniware.blog.api.board.dto.request.BoardCreate;
import com.miniware.blog.api.board.dto.request.BoardEdit;
import com.miniware.blog.api.board.dto.response.BoardResponse;
import com.miniware.blog.api.board.service.BoardService;
import com.miniware.blog.api.common.dto.DataResponseDto;
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
    public ResponseEntity<DataResponseDto<List<BoardResponse>>> getList() {
        List<BoardResponse> list = boardService.findAll();
        return ResponseEntity.ok(DataResponseDto.of(list, BOARD_RETRIEVED));
    }

    /*게시판 조회*/
    @GetMapping("/{boardId}")
    public ResponseEntity<DataResponseDto<BoardResponse>> get(@PathVariable Long boardId) {
        BoardResponse boardResponse = boardService.get(boardId);
        DataResponseDto<BoardResponse> result = DataResponseDto.of(boardResponse, BOARD_RETRIEVED);
        return ResponseEntity.ok(result);
    }

    /*게시판 등록*/
    @PostMapping
    public ResponseEntity<ResponseDto> save(@Valid @RequestBody BoardCreate boardCreate) {
        boardService.save(boardCreate);
        ResponseDto result = ResponseDto.of(BOARD_CREATED);
        return ResponseEntity.ok(result);
    }

    //게시판 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<ResponseDto> update(@PathVariable Long boardId, @Valid @RequestBody BoardEdit boardEdit) {
        boardService.edit(boardId, boardEdit);
        ResponseDto result = ResponseDto.of(BOARD_UPDATED);
        return ResponseEntity.ok(result);
    }

    //게시판 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<ResponseDto> delete(@PathVariable Long boardId) {
        boardService.delete(boardId);
        ResponseDto result = ResponseDto.of(BOARD_DELETED);
        return ResponseEntity.ok(result);
    }
}
