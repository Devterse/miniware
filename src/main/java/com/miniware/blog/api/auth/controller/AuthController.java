package com.miniware.blog.api.auth.controller;

import com.miniware.blog.api.auth.constants.AuthCode;
import com.miniware.blog.api.auth.dto.request.AuthRequest;
import com.miniware.blog.api.auth.dto.request.RefreshRequest;
import com.miniware.blog.api.auth.dto.response.AuthResponse;
import com.miniware.blog.api.auth.service.AuthService;
import com.miniware.blog.api.auth.jwt.JwtUtil;
import com.miniware.blog.api.auth.service.RefreshTokenService;
import com.miniware.blog.api.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.miniware.blog.api.user.constants.UserCode.USER_CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    //회원가입
    @PostMapping("/join")
    public ResponseEntity<ResponseDto> join(@RequestBody AuthRequest request) {
        authService.join(request);
        ResponseDto result = ResponseDto.of(USER_CREATED);
        return ResponseEntity.ok(result);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse result = authService.login(request);
        return ResponseEntity.ok(result);
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logout(@RequestBody RefreshRequest request) {
        String refreshToken = request.getRefreshToken();
        refreshTokenService.deleteRefreshToken(refreshToken);
        ResponseDto result = ResponseDto.of(AuthCode.LOGOUT_SUCCESS);
        return ResponseEntity.ok(result);
    }

    //리프레시 토큰으로 새로운 엑세스 토큰 발급
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshRequest request) {
        String refreshToken = request.getRefreshToken();
        AuthResponse result = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(result);
    }
}
