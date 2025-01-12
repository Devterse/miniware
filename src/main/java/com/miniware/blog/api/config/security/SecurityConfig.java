package com.miniware.blog.api.config.security;

import com.miniware.blog.api.auth.jwt.JwtAuthenticationFilter;
import com.miniware.blog.api.auth.service.CustomUserDetailsService;
import com.miniware.blog.api.user.constants.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider(customUserDetailsService, passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder
                .authenticationProvider(authenticationProvider())
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
        return authManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //csrf disable
        http.csrf(AbstractHttpConfigurer::disable);
        //form 로그인 방식 disable
        http.formLogin(AbstractHttpConfigurer::disable);
        //http basic 인증 방식 disable
        http.httpBasic(AbstractHttpConfigurer::disable);
        //세션 비활성화
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //경로별 인가 작업
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/api/v1/auth/login", "/","/api/v1/boards/**", "/api/v1/auth/join", "/api/v1/auth/refresh").permitAll()
                .requestMatchers("/admin").hasRole(Role.ADMIN.name())
                .anyRequest().authenticated()
        );
        http.exceptionHandling((exceptions) -> exceptions
            .authenticationEntryPoint(customAuthenticationEntryPoint)
            .accessDeniedHandler(customAccessDeniedHandler)
        );
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Collections.singletonList("http://localhost:8080"));   //허용할 도메인 URL을 설정
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));        //허용할 http메서드 설정
        config.setAllowCredentials(true);                                               //쿠키를 통한 인증 정보를 허용할지 여부 설정
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));       //클라이언트가 요청 시 사용할 수 있는 헤더를 설정
        config.setMaxAge(3600L);                                                        // 1시간 동안 캐시
        config.setExposedHeaders(Collections.singletonList("Authorization"));           //클라이언트가 응답에서 접근할 수 있는 헤더를 설정
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);    //모든 경로에 config 적용
        return source;
    }
}
