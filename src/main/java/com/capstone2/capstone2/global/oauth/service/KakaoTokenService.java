package com.capstone2.capstone2.global.oauth.service;

import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.member.repository.MemberRepository;
import com.capstone2.capstone2.global.oauth.dto.KakaoTokenResponseDTO;
import com.capstone2.capstone2.global.util.JwtUtil;
import com.capstone2.capstone2.global.util.KakaoUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoTokenService {
    private final KakaoUtil kakaoUtil;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member loginOrJoinWithKakaoData(
            KakaoTokenResponseDTO.TokenAndProfile kakaoData,
            HttpServletResponse httpServletResponse
    ) {
        String email = kakaoData.getEmail();
        Long kakaoId = kakaoData.getKakaoId();
        String nickname = kakaoData.getNickname();
        String profileUrl = kakaoData.getProfileImageUrl();

        // ① 이메일 또는 카카오 ID 기준으로 회원 조회
        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> {
                    // ② DB에 없으면 새 회원으로 저장
                    Member newMember = Member.builder()
                            .kakaoId(kakaoId)
                            .email(email)
                            .nickname(nickname)
                            .profile_url(profileUrl)
                            .build();
                    return memberRepository.save(newMember);
                });

        // ③ 자체 JWT 생성
        String jwt = jwtUtil.createAccessToken(member.getEmail());
        httpServletResponse.setHeader("Authorization", "Bearer " + jwt);

        return member;
    }
}
