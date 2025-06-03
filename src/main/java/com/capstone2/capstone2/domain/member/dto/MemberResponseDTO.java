package com.capstone2.capstone2.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

public class MemberResponseDTO {
    @Getter
    @Builder
    public static class JoinResultDTO {
        private Long id;
        private String nickname;
        private String email;

        private String token; // 테스트용
    }
}
