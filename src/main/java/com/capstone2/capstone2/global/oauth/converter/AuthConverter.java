package com.capstone2.capstone2.global.oauth.converter;

import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.global.oauth.dto.KakaoDTO;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthConverter {

    // 카카오 프로필을 받아 Member 엔티티 빌드하기
    public static Member toMember(Long kakaoId, String email, String nickname, String profileImageUrl) {
        return Member.builder()
                .kakaoId(kakaoId)
                .email(email)
                .nickname(nickname)
                .profile_url(profileImageUrl)
                .build();
    }
}
