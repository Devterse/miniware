package com.miniware.blog.api.config.security;

import com.miniware.blog.api.auth.exception.AuthException;
import com.miniware.blog.api.auth.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        // 사용자 정보 로드 및 검증
        UserDetails userDetails = validateUser(username, password);

        // 4. 인증 성공 시 UsernamePasswordAuthenticationToken 반환
        return new UsernamePasswordAuthenticationToken(
                userDetails, // 인증된 사용자 정보
                password, // 인증된 사용자 입력 비밀번호
                userDetails.getAuthorities() // 사용자 권한 정보
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    //사용자 검증
    private UserDetails validateUser(String username, String password) {
        try {
            // 사용자 로드
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            // 비밀번호 검증
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw AuthException.invalidUsernameOrPassword();
            }

            return userDetails;
        } catch (UsernameNotFoundException e) {
            throw AuthException.invalidUsernameOrPassword();
        }
    }

}
