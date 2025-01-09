package com.miniware.blog.api.auth.repository;

import com.miniware.blog.api.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository2 extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    void deleteByUsername(String token);

    Optional<RefreshToken> findByUsername(String username);

}
