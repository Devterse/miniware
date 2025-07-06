package com.miniware.blog.api.auth.service;

import com.miniware.blog.api.auth.dto.request.AuthRequest;
import com.miniware.blog.api.auth.dto.response.AuthResponse;
import com.miniware.blog.api.auth.exception.AuthException;
import com.miniware.blog.api.auth.jwt.JwtUtil;
import com.miniware.blog.api.auth.model.CustomUserDetails;
import com.miniware.blog.api.auth.repository.RefreshTokenRepository;
import com.miniware.blog.api.auth.constants.AuthConstants;
import com.miniware.blog.api.common.util.CookieUtil;
import com.miniware.blog.api.user.constants.Role;
import com.miniware.blog.api.user.entity.User;
import com.miniware.blog.api.user.exception.UserException;
import com.miniware.blog.api.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

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
    public AuthResponse login(AuthRequest request, HttpServletResponse response) {
        //사용자 인증
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) authenticate.getPrincipal();

        Long userId = userDetails.getUserId();
        String username = request.getUsername();
        Set<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        //Access Token 생성
        String accessToken = jwtUtil.createAccessToken(userId, username, roles);
        //Refresh Token 생성
        String refreshToken = jwtUtil.createRefreshToken();
        //Refresh Token 저장
        refreshTokenRepository.save(refreshToken, userId, jwtUtil.getRefreshExpiredMs());
        //Refresh Token 쿠키로 설정
        ResponseCookie cookie = CookieUtil.createCookie(AuthConstants.REFRESH_TOKEN, refreshToken, 604800, true, true);

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return AuthResponse.of(accessToken);
    }

    /**
     * Access Token 갱신
     * @param refreshToken, response
     * @return  새로운 Access Token
     */
    public AuthResponse refreshAccessToken(String refreshToken, HttpServletResponse response) {

        //refreshToken을 통해 userId조회
        Long userId = refreshTokenRepository.findUserIdByRefreshToken(refreshToken).orElseThrow(AuthException::refreshTokenInvalid);
        //사용자 정보 조회
        User user = userRepository.findById(userId).orElseThrow(UserException::notFound);
        Set<String> roles = user.getRoles().stream().map(Role::name).collect(Collectors.toSet());

        //새로운 accessToken 생성
        String newAccessToken = jwtUtil.createAccessToken(userId, user.getUsername(), roles);

        //refresh token 생성
        String newRefreshToken = jwtUtil.createRefreshToken();

        //refresh token 저장
        refreshTokenRepository.save(newRefreshToken, userId, jwtUtil.getRefreshExpiredMs());

        //이전 refreshToken 삭제
        refreshTokenRepository.delete(refreshToken);

        //쿠키 세팅
        ResponseCookie cookie = CookieUtil.createCookie(AuthConstants.REFRESH_TOKEN, newRefreshToken, 604800, true, true);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return AuthResponse.of(newAccessToken);
    }

    /**
     * refresh Token 삭제
     * @param refreshToken  response
     */
    public void logout(String refreshToken, HttpServletResponse response) {
        refreshTokenRepository.delete(refreshToken);
        ResponseCookie cookie = CookieUtil.deleteCookie(AuthConstants.REFRESH_TOKEN);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
