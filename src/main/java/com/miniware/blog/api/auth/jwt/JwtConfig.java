package com.miniware.blog.api.auth.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.crypto.SecretKey;

@Getter
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private final SecretKey secretKey;
    private final long accessExpiredMs;
    private final long refreshExpiredMs;

    public JwtConfig(String secret, long accessExpiredMs, long refreshExpiredMs) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);   //Base64 문자열을 디코딩하여 byte 배열로 변환
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);    // HMAC-SHA 알고리즘을 위한 키 생성
        this.accessExpiredMs = accessExpiredMs;
        this.refreshExpiredMs = refreshExpiredMs;
    }
}


