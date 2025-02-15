package com.miniware.blog.api.config;

import com.miniware.blog.api.auth.jwt.JwtAuthenticationFilter;
import com.miniware.blog.api.auth.service.CustomUserDetailsService;
import com.miniware.blog.api.auth.security.CustomAccessDeniedHandler;
import com.miniware.blog.api.auth.security.CustomAuthenticationEntryPoint;
import com.miniware.blog.api.auth.security.CustomAuthenticationProvider;
import com.miniware.blog.api.user.constants.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
import java.util.List;

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
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)          //csrf disable
                .formLogin(AbstractHttpConfigurer::disable) //form 로그인 방식 disable
                .httpBasic(AbstractHttpConfigurer::disable) //http basic 인증 방식 disable
                .anonymous(AbstractHttpConfigurer::disable) // 익명 사용자 기능 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //세션 비활성화
                .authorizeHttpRequests(auth -> auth //경로별 인가 작업
                        .requestMatchers("/api/v1/auth/login", "/","/api/v1/boards/**", "/api/v1/auth/join", "/api/v1/auth/refresh","api/v1/posts/**",
                                "/api/v1/auth/logout","/api/v1/users/**").permitAll()
                        .requestMatchers("/admin").hasRole(Role.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptions -> exceptions // 인증 및 인가 예외 처리
                        .authenticationEntryPoint(customAuthenticationEntryPoint) // 인증 예외 처리 강제 설정
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);// JWT 필터 적용
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:8080")); // 여러 도메인을 허용할 경우 List.of() 사용 가능
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH")); // 허용할 HTTP 메서드 설정
        config.setAllowCredentials(true); // 쿠키 인증 허용 여부 설정
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // 허용할 헤더 설정
        config.setMaxAge(3600L); // 1시간 동안 CORS 설정 캐시
        config.setExposedHeaders(List.of("Authorization")); // 클라이언트에서 접근 가능한 헤더 설정
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);    //모든 경로에 config 적용
        return source;
    }
}
