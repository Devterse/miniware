package com.devterse.miniware.api.board.controller;

import com.devterse.miniware.api.board.dto.BoardDto;
import com.devterse.miniware.api.board.dto.ReqBoardDto;
import com.devterse.miniware.api.board.dto.ReqBoardDto.SaveBoardDto;
import com.devterse.miniware.api.board.dto.ResBoardDto;
import com.devterse.miniware.api.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/boards")
public class BoardController {

    private final BoardService boardService;

    //저장
    @PostMapping
    public ResponseEntity<?> save(@RequestBody BoardDto saveBoardDto) {
        Long id = boardService.save(saveBoardDto);
        return ResponseEntity.ok(id);
    }

    //id로 조회
    @GetMapping("{id}")
    public ResponseEntity<ResBoardDto> findById(@PathVariable Long id) {
        ResBoardDto boardDto = boardService.findById(id);
        return ResponseEntity.ok(boardDto);
    }
    //수정
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id) {
        return null;
    }

    //삭제
}
