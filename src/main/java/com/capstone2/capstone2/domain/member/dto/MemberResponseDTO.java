package com.capstone2.capstone2.domain.member.dto;

import com.capstone2.capstone2.domain.location.entity.Town;
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

    // 전체 회원 정보 조회용 DTO
    @Getter
    @Builder
    public static class InfoDTO {
        private Long id;
        private String email;
        private String nickname;
        private Long kakaoId;
        private String profileUrl;
        private String mainCategory;
        private String subCategory;
        private Long currentTownId;
    }

}
