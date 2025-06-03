package com.capstone2.capstone2.global.oauth.controller;

import com.capstone2.capstone2.domain.member.converter.MemberConverter;
import com.capstone2.capstone2.domain.member.dto.MemberResponseDTO;
import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.member.service.MemberService;
import com.capstone2.capstone2.global.common.response.ApiResponse;
import com.capstone2.capstone2.global.error.code.status.SuccessStatus;
import com.capstone2.capstone2.global.oauth.dto.KakaoDTO;
import com.capstone2.capstone2.global.oauth.dto.KakaoTokenResponseDTO;
import com.capstone2.capstone2.global.oauth.service.KakaoTokenService;
import com.capstone2.capstone2.global.util.JwtUtil;
import com.capstone2.capstone2.global.util.KakaoUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/kakao")
@RequiredArgsConstructor
public class KakaoTokenController {

    private final KakaoUtil kakaoUtil;
    private final KakaoTokenService kakaoTokenService;
    private final JwtUtil jwtUtil;

    /**
     * 1단계: 클라이언트가 인가 코드(code)를 이 엔드포인트에 전달하면,
     *       카카오 서버에 POST /oauth/token → 토큰 획득,
     *       토큰으로 카카오 프로필 조회 → 프로필 획득 후
     *       토큰과 프로필 정보를 모두 묶어서 리턴한다.
     *
     * API 경로: GET /auth/kakao/token?code={인가코드}
     */
    @GetMapping("/token")
    public KakaoTokenResponseDTO.TokenAndProfile getTokenAndProfile(
            @RequestParam("code") String code
    ) {
        // 1) 카카오 서버로 토큰 교환 요청
        KakaoDTO.OAuthToken oAuthToken = kakaoUtil.requestToken(code);

        // 2) 발급받은 Access Token으로 프로필 조회
        KakaoDTO.KakaoProfile kakaoProfile = kakaoUtil.requestProfile(oAuthToken);

        // 3) 토큰 정보와 프로필 정보를 DTO로 묶어서 리턴
        KakaoTokenResponseDTO.TokenAndProfile dto = new KakaoTokenResponseDTO.TokenAndProfile(
                oAuthToken.getAccess_token(),
                oAuthToken.getRefresh_token(),
                (long) oAuthToken.getExpires_in(),
                (long) oAuthToken.getRefresh_token_expires_in(),
                kakaoProfile.getId(),
                kakaoProfile.getKakao_account().getEmail(),
                kakaoProfile.getKakao_account().getProfile().getProfile_image_url(), // or getProfile_image_url()
                kakaoProfile.getProperties().getNickname()
        );
        return dto;
    }

    @PostMapping("/login-join")
    public ApiResponse<MemberResponseDTO.JoinResultDTO> loginOrJoin(
            @RequestBody KakaoTokenResponseDTO.TokenAndProfile kakaoData,
            HttpServletResponse httpServletResponse
    ) {
        // AuthService 쪽에서 “kakaoData”를 받아서 DB 조회/저장 → 자체 JWT 발급 → 응답 헤더에 JWT 담음
        Member member = kakaoTokenService.loginOrJoinWithKakaoData(kakaoData, httpServletResponse);
        String jwt = jwtUtil.createAccessToken(member.getEmail());
        httpServletResponse.setHeader("Authorization", "Bearer " + jwt);

        MemberResponseDTO.JoinResultDTO dto = MemberConverter.toJoinResultDTO_WithToken(member, jwt);
        return ApiResponse.onSuccess(
                SuccessStatus.USER_EMAIL_LOGIN_OK,
                dto
        );
    }
}
