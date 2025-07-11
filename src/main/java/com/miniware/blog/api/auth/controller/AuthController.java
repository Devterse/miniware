package com.miniware.blog.api.auth.controller;

import com.miniware.blog.api.auth.dto.request.AuthRequest;
import com.miniware.blog.api.auth.dto.response.AuthResponse;
import com.miniware.blog.api.auth.service.AuthService;
import com.miniware.blog.api.auth.constants.AuthConstants;
import com.miniware.blog.api.common.dto.ResponseDto;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<ResponseDto<AuthResponse>> login(@Valid @RequestBody AuthRequest request, HttpServletResponse response) {
        AuthResponse authResponse = authService.login(request, response);
        ResponseDto<AuthResponse> result = ResponseDto.of(LOGIN_SUCCESS, authResponse);
        return ResponseEntity.ok(result);
    }

    //Refresh Token을 사용해 Access Token 갱신
    @PostMapping("/refresh")
    public ResponseEntity<ResponseDto<AuthResponse>> refreshToken(
            @CookieValue(value = AuthConstants.REFRESH_TOKEN, required = false) String refreshToken,
            HttpServletResponse response) {
        AuthResponse authResponse = authService.refreshAccessToken(refreshToken, response);
        ResponseDto<AuthResponse> result = ResponseDto.of(ACCESS_TOKEN_SUCCESS, authResponse);
        return ResponseEntity.ok(result);
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<Void>> logout(
            @CookieValue(value = AuthConstants.REFRESH_TOKEN, required = false) String refreshToken,
            HttpServletResponse response) {
        authService.logout(refreshToken, response);
        ResponseDto<Void> result = ResponseDto.of(LOGOUT_SUCCESS);
        return ResponseEntity.ok(result);
    }

}
