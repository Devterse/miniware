package com.miniware.blog.api.common.exception;

import com.miniware.blog.api.common.constant.CodeData;
import com.miniware.blog.api.common.constant.CommonCode;
import com.miniware.blog.api.common.dto.ErrorResponseDto;
import com.miniware.blog.api.common.dto.ValidationResponseDto;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //커스텀 예외처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> customException(CustomException ex, WebRequest request) {
        CodeData result = ex.getCodeData();
        ErrorResponseDto response = ErrorResponseDto.of(result);
        return ResponseEntity.status(result.getHttpStatus()).body(response);
    }

    //유효성 검증 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        ValidationResponseDto response = ValidationResponseDto.of(bindingResult);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //데이터베이스 처리 예외처리
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(DataAccessException ex) {
        ErrorResponseDto response = ErrorResponseDto.of(CommonCode.INTERNAL_ERROR, ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // 접근 거부 예외 처리
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근이 거부되었습니다: " + ex.getMessage());
    }

    // 데이터 불일치 예외 처리 (예: 데이터베이스에서 찾을 수 없는 ID로 조회할 경우)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("데이터 불일치: " + ex.getMessage());
    }

    // 잘못된 요청 예외 처리 (예: 잘못된 형식의 데이터를 클라이언트가 전송한 경우)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청: " + ex.getMessage());
    }

    // 자원을 찾을 수 없는 경우의 예외 처리
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 자원을 찾을 수 없습니다: " + ex.getMessage());
    }

    // 모든 예외 처리 (앞서 정의되지 않은 예외들)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllUncaughtException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("내부 서버 오류: " + ex.getMessage());
    }

}
