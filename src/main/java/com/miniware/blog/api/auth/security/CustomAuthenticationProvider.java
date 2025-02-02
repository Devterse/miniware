package com.miniware.blog.api.auth.security;

import com.miniware.blog.api.auth.constants.AuthCode;
import com.miniware.blog.api.auth.exception.AuthException;
import com.miniware.blog.api.auth.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.miniware.blog.api.auth.constants.AuthCode.LOGIN_FAILURE;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // 사용자 로드
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        // 비밀번호 검증
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException(LOGIN_FAILURE.getMessage());
        }

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

}
