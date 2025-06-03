package com.capstone2.capstone2.global.oauth.controller;

import com.capstone2.capstone2.domain.member.converter.MemberConverter;
import com.capstone2.capstone2.domain.member.dto.MemberResponseDTO;
import com.capstone2.capstone2.domain.member.dto.MemberResponseDTO.JoinResultDTO;
import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.member.repository.MemberRepository;
import com.capstone2.capstone2.global.error.code.status.ErrorStatus;
import com.capstone2.capstone2.global.oauth.dto.KakaoDTO;
import com.capstone2.capstone2.global.oauth.dto.KakaoTokenResponseDTO;
import com.capstone2.capstone2.global.oauth.exception.AuthException;
import com.capstone2.capstone2.global.oauth.service.AuthService;
import com.capstone2.capstone2.global.util.KakaoUtil;
import com.capstone2.capstone2.global.oauth.service.KakaoTokenService;
import com.capstone2.capstone2.global.util.JwtUtil;
import com.capstone2.capstone2.global.common.response.ApiResponse;
import com.capstone2.capstone2.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class KakaoTestController {

    private final KakaoUtil kakaoUtil;
    private final KakaoTokenService kakaoTokenService;
    private final JwtUtil jwtUtil;
    private final AuthService authService;

    @GetMapping("/auth/login/kakao-test")
    public void kakaoTestLogin(
            @RequestParam("code") String code,
            HttpServletResponse response
    ) throws IOException {
        // 1) 카카오 서버에서 인가 코드로 Access/Refresh Token 교환
        KakaoDTO.OAuthToken oAuthToken = kakaoUtil.requestToken(code);

        // 2) 교환된 Access Token으로 프로필 조회
        KakaoDTO.KakaoProfile kakaoProfile = kakaoUtil.requestProfile(oAuthToken);

        // 3) 프로필 정보로 DB 가입/로그인
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
        Member member = kakaoTokenService.loginOrJoinWithKakaoData(kakaoData, response);

        // 4) 자체 JWT 생성
        String jwt = jwtUtil.createAccessToken(member.getEmail());

        // 5) 인가 코드 소진 문제 방지를 위해, 성공 페이지로 리다이렉트
        //    브라우저가 아래 주소(/kakao-success?token=…​)로 자동으로 이동하게 됨
        String redirectUrl = "/kakao-success?token=" + jwt;
        response.sendRedirect(redirectUrl);
    }



    @GetMapping("/auth/user")
    public ApiResponse<MemberResponseDTO.JoinResultDTO> getLoginUser() {
        // 1) 현재 로그인된 Member 엔티티 조회
        Member member = authService.getLoginUser();

        // 2) Member → DTO 변환
        MemberResponseDTO.JoinResultDTO dto = authService.toCurrentUserDto(member);

        // 3) ApiResponse 형태로 감싸서 반환
        return ApiResponse.onSuccess(SuccessStatus.KAKAO_USER_FETCH_OK, dto);
    }
}

