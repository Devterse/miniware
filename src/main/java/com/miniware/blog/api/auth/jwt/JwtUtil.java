package com.miniware.blog.api.auth.jwt;

import com.miniware.blog.api.user.constants.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtConfig jwtConfig;

    public String createAccessToken(Long userId, String username, Set<Role> roles) {
        return createToken(userId, username, roles, getAccessExpiredMs());
    }
    public String createRefreshToken() {
        return UUID.randomUUID().toString(); // UUID로 생성
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public long getAccessExpiredMs() {
        return jwtConfig.getAccessExpiredMs();
    }

    public long getRefreshExpiredMs() {
        return jwtConfig.getRefreshExpiredMs();
    }

    public Set<Role> extractRoles(String token) {
        Set<String> roles = extractClaim(token, claims -> (Set<String>) claims.get("roles"));
        return roles.stream().map(Role::valueOf).collect(Collectors.toSet());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    //JWT 토큰 생성 메서드
    public String createToken(Long userId, String username, Set<Role> roles, Long expiredMs) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles.stream().map(Role::name).collect(Collectors.toSet()));
        claims.put("userId", userId);

        Instant now = Instant.now();
        return Jwts.builder()
                .subject(username)
                .claims(claims)
                .issuedAt(Date.from(now)) //발급 시간 설정
                .expiration(Date.from(now.plusMillis(expiredMs)))  //만료 시간 설정
                .signWith(jwtConfig.getSecretKey()).compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())  //SecretKey로 검증 설정
                .build()
                .parseSignedClaims(token)   //토큰에서 클레임 파싱
                .getPayload();              //클레임 페이로드 변환
    }

}
