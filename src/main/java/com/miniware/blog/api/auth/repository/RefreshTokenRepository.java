package com.miniware.blog.api.auth.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {
    private final String PREFIX_REFRESH_TOKEN = "refresh:"; // Key 네임스페이스
    private final StringRedisTemplate redisTemplate;

    //Refresh Token 저장
    public void saveRefreshToken(Long userId, String refreshToken, long ttl) {
        String key = PREFIX_REFRESH_TOKEN + userId; //"refresh:{userId}" 형태의 키
        redisTemplate.opsForValue().set(key, refreshToken, ttl, TimeUnit.SECONDS); // TTL 설정
    }

    //Refresh Token 조회
    public String getRefreshToken(Long userId) {
        String key = PREFIX_REFRESH_TOKEN + userId;
        return redisTemplate.opsForValue().get(key);
    }

    //Refresh Token 삭제
    public void deleteRefreshToken(Long userId) {
        String key = PREFIX_REFRESH_TOKEN + userId;
        redisTemplate.delete(key);
    }

    public boolean validateRefreshToken(Long userId, String providedToken) {
        String storedToken = getRefreshToken(userId);
        return storedToken != null && storedToken.equals(providedToken);
    }

    public Long getUserIdFromRefreshToken(String refreshToken) {
        String key = PREFIX_REFRESH_TOKEN + refreshToken;
        return Long.parseLong(Objects.requireNonNull(redisTemplate.opsForValue().get(key)));
    }

}
