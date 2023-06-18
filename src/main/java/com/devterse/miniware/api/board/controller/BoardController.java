package com.devterse.miniware.api.board.controller;

import com.devterse.miniware.api.board.dto.ReqSaveBoardDto;
import com.devterse.miniware.api.board.dto.ReqUpdateBoardDto;
import com.devterse.miniware.api.board.dto.ResBoardDto;
import com.devterse.miniware.api.board.service.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "Board")
@RequestMapping("api/v1/boards")
public class BoardController {

    private final BoardService boardService;

    //저장
    @ApiOperation(value = "게시판 등록", notes = "게시판 등록 Post API")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody ReqSaveBoardDto dto) {
        Long id = boardService.save(dto);
        return ResponseEntity.ok(id);
    }

    //id로 조회
    @ApiOperation(value = "게시판 조회", notes = "게시판 조회 Get API")
    @GetMapping("{id}")
    public ResponseEntity<ResBoardDto> findById(@PathVariable Long id) {
        ResBoardDto boardDto = boardService.findById(id);
        return ResponseEntity.ok(boardDto);
    }

    //수정
    @ApiOperation(value = "게시판 수정", notes = "게시판 수정 Put API")
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ReqUpdateBoardDto dto) {
        return ResponseEntity.ok(boardService.update(id, dto));
    }

    //삭제
    @ApiOperation(value = "게시판 삭제", notes = "게시판 삭제 Delete API")
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.delete(id));
    }
}
