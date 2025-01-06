package com.miniware.blog.api.auth.service;

import com.miniware.blog.api.auth.dto.request.AuthRequest;
import com.miniware.blog.api.auth.dto.response.AuthResponse;
import com.miniware.blog.api.auth.jwt.JwtUtil;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    //회원가입
    @Transactional
    public void join(AuthRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw UserException.duplicate();
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singleton(Role.USER))
                .build();
        userRepository.save(user);
    }

    //로그인
    public AuthResponse login(AuthRequest request) {
        //사용자 인증
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        Collection<? extends GrantedAuthority> authorities = authenticate.getAuthorities();
        Set<Role> roles = authorities.stream()
                .map(grantedAuthority -> Role.valueOf(grantedAuthority.getAuthority()))
                .collect(Collectors.toSet());

        //access token 생성
        String accessToken = jwtUtil.createAccessToken(request.getUsername(), roles);
        //refresh token 생성
        String refreshToken = jwtUtil.createRefreshToken();
        //refresh token 저장
        refreshTokenService.saveRefreshToken(request.getUsername(), refreshToken, jwtUtil.getRefreshExpiredMs());

        return AuthResponse.of(accessToken, refreshToken);
    }

    //
    public AuthResponse refreshAccessToken(String refreshToken) {
        if (refreshTokenService.validateRefreshToken(refreshToken)) {
            String username = refreshTokenService.getUsernameByRefreshToken(refreshToken);
            String newAccessToken = jwtUtil.createAccessToken(username, Collections.singleton(Role.USER));
            return AuthResponse.of(newAccessToken, refreshToken);
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }
}
