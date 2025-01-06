package com.miniware.blog.api.auth.service;

import com.miniware.blog.api.auth.entity.RefreshToken;
import com.miniware.blog.api.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final StringRedisTemplate redisTemplate;

    //Refresh Token 저장
    public void saveRefreshToken(String username, String refreshToken, long refreshExpiredMs) {
        String key = "refresh:" + username;
        redisTemplate.opsForValue().set(key, refreshToken, Duration.ofMillis(refreshExpiredMs));
    }

    public boolean validateRefreshToken(String username, String refreshToken) {
        String key = "refresh:" + username;
        String storedToken = redisTemplate.opsForValue().get(key);
        return refreshToken.equals(storedToken);
    }

    public void deleteRefreshToken(String username) {
        String key = "refresh:" + username;
        redisTemplate.delete(key);
    }

    //refresh token 유효성 검사
    public boolean validateRefreshToken(String refreshToken) {
        Optional<RefreshToken> RefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken);
        return RefreshToken.isPresent() && RefreshToken.get().getExpiresAt().isAfter(Instant.now());
    }

    //토큰으로 부터 사용자 username 추출
    public String getUsernameByRefreshToken(String refreshToken) {
        Optional<RefreshToken> result = refreshTokenRepository.findByRefreshToken(refreshToken);
        return result.map(RefreshToken::getUsername).orElse(null);
    }

}
