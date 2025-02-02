package com.miniware.blog.api.auth.service;

import com.miniware.blog.api.auth.dto.request.AuthRequest;
import com.miniware.blog.api.auth.dto.request.RefreshRequest;
import com.miniware.blog.api.auth.dto.response.AuthResponse;
import com.miniware.blog.api.auth.exception.AuthException;
import com.miniware.blog.api.auth.jwt.JwtUtil;
import com.miniware.blog.api.auth.model.CustomUserDetails;
import com.miniware.blog.api.auth.repository.RefreshTokenRepository;
import com.miniware.blog.api.user.constants.Role;
import com.miniware.blog.api.user.entity.User;
import com.miniware.blog.api.user.exception.UserException;
import com.miniware.blog.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 로그인 처리
     * @param request 사용자 이름, 사용자 비밀번호
     * @return Access Token과 Refresh Token
     */
    public AuthResponse login(AuthRequest request) {
        //사용자 인증
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) authenticate.getPrincipal();
        Long userId = userDetails.getUserId();

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        Set<Role> roles = authorities.stream()
                .map(grantedAuthority -> Role.valueOf(grantedAuthority.getAuthority()))
                .collect(Collectors.toSet());

        //access token 생성
        String accessToken = jwtUtil.createAccessToken(userId, request.getUsername(), roles);
        //refresh token 생성
        String refreshToken = jwtUtil.createRefreshToken();
        //refresh token 저장
        refreshTokenRepository.saveRefreshToken(userId, refreshToken, jwtUtil.getRefreshExpiredMs());
        return AuthResponse.of(accessToken, refreshToken);
    }

    /**
     * Access Token 갱신
     * @param request 사용자 ID, refreshToken
     * @return  새로운 Access Token
     */
    public AuthResponse refreshAccessToken(RefreshRequest request) {
        String refreshToken = request.getRefreshToken();
        Long userId = request.getUserId();

        // Refresh Token 검증
        if(!refreshTokenRepository.validateRefreshToken(userId, refreshToken)) {
            throw AuthException.refreshTokenInvalid();
        }

        //사용자 정보 조회
        User user = userRepository.findById(userId).orElseThrow(UserException::notFound);

        //새로운 accessToken 생성
        String newAccessToken = jwtUtil.createAccessToken(userId, user.getUsername(), user.getRoles());
        String newRefreshToken = jwtUtil.createRefreshToken();

        return AuthResponse.of(newAccessToken, newRefreshToken);
    }

    public void logout(RefreshRequest request) {
        refreshTokenRepository.deleteRefreshToken(request.getUserId());
    }
}
