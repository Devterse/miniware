package com.miniware.blog.api.auth.controller;

import com.miniware.blog.api.auth.dto.request.AuthRequest;
import com.miniware.blog.api.auth.dto.request.RefreshRequest;
import com.miniware.blog.api.auth.dto.response.AuthResponse;
import com.miniware.blog.api.auth.service.AuthService;
import com.miniware.blog.api.common.dto.DataResponseDto;
import com.miniware.blog.api.common.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.miniware.blog.api.auth.constants.AuthCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    //로그인
    @PostMapping("/login")
    public ResponseEntity<DataResponseDto<AuthResponse>> login(@Valid @RequestBody AuthRequest request) {
        AuthResponse authResponse = authService.login(request);
        DataResponseDto<AuthResponse> result = DataResponseDto.of(authResponse, LOGIN_SUCCESS);
        return ResponseEntity.ok(result);
    }

    //Refresh Token을 사용해 Access Token 갱신
    @PostMapping("/refresh")
    public ResponseEntity<DataResponseDto<AuthResponse>> refreshToken(@RequestBody RefreshRequest request) {
        AuthResponse authResponse = authService.refreshAccessToken(request);
        DataResponseDto<AuthResponse> result = DataResponseDto.of(authResponse, ACCESS_TOKEN_SUCCESS);
        return ResponseEntity.ok(result);
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logout(@RequestBody RefreshRequest request) {
        authService.logout(request);
        ResponseDto result = ResponseDto.of(LOGOUT_SUCCESS);
        return ResponseEntity.ok(result);
    }

}
