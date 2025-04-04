package com.miniware.blog.api.auth.jwt;

import com.miniware.blog.api.auth.exception.AuthException;
import com.miniware.blog.api.auth.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String jwtToken = null;
        String username = null;

        if(authorization != null && authorization.startsWith("Bearer ")) {
            jwtToken = authorization.substring(7);
            try{
                username = jwtUtil.extractUsername(jwtToken);
            }catch (Exception e) {
                throw AuthException.tokenParseError();
            }
        }

        //현재 사용자가 인증되지 않은 상태라면 jwt를 확인
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //데이터베이스에서 UserDetails 객체를 가져옴
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            //JWT가 유효한지 검증
            if(jwtUtil.validateToken(jwtToken,userDetails.getUsername())) {
                //JWT가 유효하면 인증 객체를 생성
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                //인증 객체를 SecurityContextHolder에 저장
                //이후 Spring Security가 해당 요청을 인증된 사용자로 인식
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
