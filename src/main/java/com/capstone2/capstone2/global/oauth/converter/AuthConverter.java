package com.capstone2.capstone2.global.oauth.converter;

import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.global.oauth.dto.KakaoDTO;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthConverter {

    // 카카오 프로필을 받아 Member 엔티티 빌드하기
    public static Member toMember(String email, String nickname) {
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .build();
    }
}
