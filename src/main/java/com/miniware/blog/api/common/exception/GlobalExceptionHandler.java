package com.miniware.blog.api.common.exception;

import com.miniware.blog.api.common.constant.CodeData;
import com.miniware.blog.api.common.constant.ResponseCode;
import com.miniware.blog.api.common.dto.ResponseDto;
import com.miniware.blog.api.common.dto.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

import static com.miniware.blog.api.auth.constants.AuthCode.*;
import static com.miniware.blog.api.common.constant.ResponseCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //커스텀 예외처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDto<Void>> customException(CustomException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        CodeData result = ex.getCodeData();
        ResponseDto<Void> response = ResponseDto.of(result);
        return ResponseEntity.status(result.getHttpStatus()).body(response);
    }

    //유효성 검증 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<List<ValidationError>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        ResponseDto<List<ValidationError>> response = ResponseDto.validationError(bindingResult);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //데이터 처리 예외처리
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ResponseDto<Void>> handleDataAccessException(DataAccessException ex) {
        log.error(ex.getMessage(), ex);
        ResponseDto<Void> response = ResponseDto.of(INTERNAL_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    //Request값이 잘못된 형식일때
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseDto<Void>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error(ex.getMessage(), ex);
        ResponseDto<Void> response = ResponseDto.of(BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //null예외처리
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseDto<Void>> NullPointerException(NullPointerException ex) {
        log.error(ex.getMessage(), ex);
        ResponseDto<Void> response = ResponseDto.of(ResponseCode.NULL_POINTER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    //password가 틀린경우
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseDto<Void>> handleBadRequestException(BadCredentialsException ex) {
        log.error(ex.getMessage(), ex);
        ResponseDto<Void> response = ResponseDto.of(LOGIN_FAILURE);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Void>> handleAllUncaughtException(Exception ex) {
        log.error(ex.getMessage(), ex);
        ResponseDto<Void> response = ResponseDto.of(BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //인증 실패시
    /*@ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseDto> handleAuthenticationException(AuthenticationException ex) {
        log.error(ex.getMessage(), ex);
        ResponseDto response = ResponseDto.of(ResponseCode.UNAUTHORIZED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }*/

    //username이 존재하지 않는 경우
    /*@ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseDto> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        ResponseDto responseDto = ResponseDto.of(ResponseCode.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }*/

}
