package com.capstone2.capstone2.global.filter;

import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.member.repository.MemberRepository;
import com.capstone2.capstone2.global.error.code.status.ErrorStatus;
import com.capstone2.capstone2.global.oauth.exception.AuthException;
import com.capstone2.capstone2.global.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * 매 요청마다 헤더에 담긴 JWT를 파싱하여 인증(Authentication) 객체를 SecurityContext에 세팅합니다.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, MemberRepository memberRepository) {
        this.jwtUtil = jwtUtil;
        this.memberRepository = memberRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 1) Authorization 헤더에서 "Bearer {token}" 꺼내기
        String header = request.getHeader("Authorization");
        if (!StringUtils.hasText(header) || !header.startsWith("Bearer ")) {
            // 헤더가 없거나, Bearer로 시작하지 않으면 인증 처리 없이 다음 필터로 넘어감
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        // 2) JWT 유효성 검사
        if (!jwtUtil.validateToken(token)) {
            // 토큰이 유효하지 않으면 401 Unauthorized 응답을 직접 보내거나 Exception을 던질 수 있음
            // 여기서는 그냥 다음 필터로 넘겨서 결국 인증이 되지 않은 상태가 됨
            filterChain.doFilter(request, response);
            return;
        }

        // 3) 토큰에서 이메일(subject) 꺼내기
        String email = jwtUtil.getUserIdFromToken(token);
        if (email == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 4) DB에서 Member 조회 (필요 시 예외 처리)
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(ErrorStatus.AUTH_INVALID_CODE));

        // 5) 인증 권한(roles) 설정 (간단히 MEMBER 권한 하나로 처리 예시)
        // 실제로는 Member 엔티티에 Role 정보가 있으면 그걸 GrantedAuthority로 변환해야 함
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_MEMBER");

        // 6) Authentication 객체 생성 및 SecurityContext에 저장
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                email,            // principal: 여기서는 이메일(또는 ID)
                null,             // credentials: JWT이므로 비밀번호는 null
                Collections.singletonList(authority)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 다음 필터로 진행
        filterChain.doFilter(request, response);
    }
}
