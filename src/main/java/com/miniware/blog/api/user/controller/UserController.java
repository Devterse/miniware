package com.miniware.blog.api.user.controller;

import com.miniware.blog.api.common.dto.DataResponseDto;
import com.miniware.blog.api.auth.jwt.JwtUtil;
import com.miniware.blog.api.user.dto.request.UserAuth;
import com.miniware.blog.api.user.dto.request.UserJoin;
import com.miniware.blog.api.user.dto.response.UserJoinResponse;
import com.miniware.blog.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.miniware.blog.api.user.constants.UserCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/join")
    public ResponseEntity<DataResponseDto<UserJoinResponse>> join(@RequestBody UserJoin request) {
        UserJoinResponse userJoinResponse = userService.join(request);
        DataResponseDto<UserJoinResponse> result = DataResponseDto.of(userJoinResponse, USER_CREATED);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserAuth request) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
//        String accessToken = jwtUtil.createAccessToken(request.getUsername(), userService.getRoles(request.getUsername()));
//        String refreshToken = jwtUtil.createRefreshToken(request.getUsername());

        //로그인시응답일때 Authorization 헤더에 Bearer <accessToken> 는 설정안해도됨
        //로그인 이후 클라이언트에서 서버로 요청보낼때 Authorization 헤더에 Bearer <accessToken> 설정하여 요청
        return null;
    }
}
