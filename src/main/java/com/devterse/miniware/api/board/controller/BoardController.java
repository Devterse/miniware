package com.devterse.miniware.api.board.controller;

import com.devterse.miniware.api.board.dto.ReqBoardDto;
import com.devterse.miniware.api.board.dto.ReqBoardDto.SaveBoardDto;
import com.devterse.miniware.api.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/boards")
public class BoardController {

    private final BoardService boardService;

    //전체 조회


    //저장
    @PostMapping
    public ResponseEntity<?> save(@RequestBody SaveBoardDto saveBoardDto) {
        Long id = boardService.save(saveBoardDto);
        return ResponseEntity.ok(id);
    }

    //id로 조회

    //수정

    //삭제
}
