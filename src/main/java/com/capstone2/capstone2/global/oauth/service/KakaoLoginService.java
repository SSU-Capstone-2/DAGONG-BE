package com.capstone2.capstone2.global.oauth.service;

import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.global.oauth.dto.KakaoDTO;
import com.capstone2.capstone2.global.oauth.dto.KakaoTokenResponseDTO;
import com.capstone2.capstone2.global.util.JwtUtil;
import com.capstone2.capstone2.global.util.KakaoUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class KakaoLoginService {

    private final KakaoUtil kakaoUtil;
    private final JwtUtil jwtUtil;
    private final KakaoTokenService kakaoTokenService;

    public void kakaoTestLogin(String code, HttpServletResponse response) throws IOException {
        // 1. 인가 코드로 토큰 발급
        KakaoDTO.OAuthToken oAuthToken = kakaoUtil.requestToken(code);

        // 2. 토큰으로 프로필 정보 조회
        KakaoDTO.KakaoProfile kakaoProfile = kakaoUtil.requestProfile(oAuthToken);

        // 3. 필요한 정보 DTO로 포장
        KakaoTokenResponseDTO.TokenAndProfile kakaoData = new KakaoTokenResponseDTO.TokenAndProfile(
                oAuthToken.getAccess_token(),
                oAuthToken.getRefresh_token(),
                (long) oAuthToken.getExpires_in(),
                (long) oAuthToken.getRefresh_token_expires_in(),
                kakaoProfile.getId(),
                kakaoProfile.getKakao_account().getEmail(),
                kakaoProfile.getKakao_account().getProfile().getProfile_image_url(),
                kakaoProfile.getProperties().getNickname()
        );

        // 4. 로그인/회원가입 처리
        Member member = kakaoTokenService.loginOrJoinWithKakaoData(kakaoData, response);

        // 5. 자체 토큰 생성 → redirect
        String jwt = jwtUtil.createAccessToken(member.getEmail());
        String redirectUrl = "/kakao-success?token=" + jwt;
        response.sendRedirect(redirectUrl);
    }
}
