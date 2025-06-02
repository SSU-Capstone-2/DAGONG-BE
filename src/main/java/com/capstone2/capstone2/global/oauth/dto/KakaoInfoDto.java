package com.capstone2.capstone2.global.oauth.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 카카오 사용자 정보 조회 응답을 파싱하기 위한 DTO
@Getter
@NoArgsConstructor
public class KakaoInfoDto {
    private Long id;
    private String nickname;
    private String profileImageUrl;

    public KakaoInfoDto(Long id, String nickname, String profileImageUrl) {
        this.id = id;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;

    }
}
