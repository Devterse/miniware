package com.miniware.blog.api.common.exception;

import com.miniware.blog.api.common.constant.CodeData;
import com.miniware.blog.api.common.constant.CommonCode;
import com.miniware.blog.api.common.dto.ErrorResponseDto;
import com.miniware.blog.api.common.dto.ValidationResponseDto;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    //데이터 처리 예외처리
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponseDto> handleDataAccessException(DataAccessException ex) {
        ErrorResponseDto response = ErrorResponseDto.of(CommonCode.INTERNAL_ERROR, ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    //Request값이 잘못된 형식일때
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ErrorResponseDto response = ErrorResponseDto.of(CommonCode.BAD_REQUEST, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleAllUncaughtException(Exception ex) {
        ErrorResponseDto response = ErrorResponseDto.of(CommonCode.BAD_REQUEST, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
