package com.miniware.blog.api.auth.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {
    private final String PREFIX_REFRESH_TOKEN = "refresh:"; // Key 네임스페이스
    private final StringRedisTemplate redisTemplate;

    /**
     * Refresh Token 저장
     *
     * @param userId 사용자 ID (Redis의 키에 사용)
     * @param refreshToken 저장할 Refresh Token
     * @param ttl TTL (Time-To-Live) 만료 시간 (초 단위)
     */
    public void save(long userId, String refreshToken, long ttl) {
        String key = PREFIX_REFRESH_TOKEN + userId; // "refresh:{userId}" 형태의 키
        redisTemplate.opsForValue().set(key, refreshToken, ttl, TimeUnit.SECONDS); // TTL 설정
    }

    /**
     * Refresh Token 조회
     *
     * @param userId 사용자 ID
     * @return 저장된 Refresh Token (없으면 null)
     */
    public String findByUserId(long userId) {
        String key = PREFIX_REFRESH_TOKEN + userId;
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Refresh Token으로 userId 조회
     * @param refreshToken 클라이언트가 제공한 Refresh Token
     * @return userId (없으면 null)
     */
    public Long findUserIdByRefreshToken(String refreshToken) {
        String key = PREFIX_REFRESH_TOKEN + refreshToken;
        String userId = redisTemplate.opsForValue().get(key);
        return userId != null ? Long.valueOf(userId) : null;
    }

    /**
     * Refresh Token 삭제
     *
     * @param userId 사용자 ID
     */
    public void delete(long userId) {
        String key = PREFIX_REFRESH_TOKEN + userId;
        redisTemplate.delete(key);
    }

    /**
     * Refresh Token 존재 여부 확인
     *
     * @param userId 사용자 ID
     * @return Refresh Token 존재 여부
     */
    public boolean exists(long userId) {
        String key = PREFIX_REFRESH_TOKEN + userId;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * TTL 확인
     *
     * @param userId 사용자 ID
     * @return 남은 TTL (초 단위), 없으면 -2 (키 없음), -1 (TTL 없음)
     */
    public Long getTTL(long userId) {
        String key = PREFIX_REFRESH_TOKEN + userId;
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * Refresh Token 검증
     * @param userId 사용자 ID
     * @param refreshToken 클라이언트가 제공한 Refresh Token
     * @return 유효 여부 (true: 유효, false: 유효하지 않음)
     */
    public boolean validateRefreshToken(Long userId, String refreshToken) {
        String key = PREFIX_REFRESH_TOKEN + userId;
        String storedToken = redisTemplate.opsForValue().get(key);

        // 저장된 토큰과 클라이언트 제공 토큰 비교
        return storedToken != null && storedToken.equals(refreshToken);
    }


}
