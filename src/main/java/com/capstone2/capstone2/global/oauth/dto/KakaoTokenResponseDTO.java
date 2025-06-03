package com.capstone2.capstone2.global.oauth.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *  카카오 인가 코드(Authorization Code)를 이용해서
 *  카카오 서버로부터 받은 Access Token, Refresh Token과
 *  그 토큰으로 다시 카카오 프로필을 조회해서 얻은 정보를
 *  그대로 클라이언트에게 돌려줄 때 사용하는 DTO
 */
@Getter
@NoArgsConstructor
public class KakaoTokenResponseDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TokenAndProfile {
        // 카카오 서버로부터 받은 토큰
        private String accessToken;
        private String refreshToken;
        private Long expiresIn;            // Access Token 만료 시간(ms 또는 초, 필요하다면)
        private Long refreshTokenExpiresIn; // Refresh Token 만료 시간(ms 또는 초, 필요하다면)

        // 카카오 프로필 정보
        private Long kakaoId;
        private String email;
        private String nickname;
        private String profileImageUrl;

        // 필요하다면 추가적으로 kakao_account 안의 다른 필드를 더 넣어도 됩니다.
    }
}
