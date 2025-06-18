package com.capstone2.capstone2.global.config;

import com.capstone2.capstone2.domain.member.repository.MemberRepository;
import com.capstone2.capstone2.global.filter.JwtAuthenticationFilter;
import com.capstone2.capstone2.global.oauth.handler.AuthHandler;
import com.capstone2.capstone2.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthHandler authHandler;
    private final JwtUtil jwtUtil;                 // JWT 유틸리티
    private final MemberRepository memberRepository; // Member 조회용 리포지토리


    private static final String[] SECURITY_ALLOW_ARRAY  = {
            "/api/login",
            "/health",
            "/error",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/login",
            "/refresh",
            "/search/**",
            "/purchases/**",
            "/auth/login/kakao",
            "/auth/login/kakao-test",
            "/auth/kakao/token",         // 카카오 인가 코드를 받아서 토큰&프로필 반환
            "/auth/kakao/login-join",     // 카카오 토큰&프로필 받아서 가입/로그인(자체 JWT 발급)
            "/auth/login/kakao-test",
            "/kakao-success",
            "/auth/user",
            "/member/**",
            "/categories/**",
            "/ws/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session
                        -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(SECURITY_ALLOW_ARRAY).permitAll()
                        .anyRequest().authenticated()

                )
                .exceptionHandling(e -> e.authenticationEntryPoint(authHandler))
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtUtil, memberRepository),
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of("*", "http://localhost:3000"));
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
