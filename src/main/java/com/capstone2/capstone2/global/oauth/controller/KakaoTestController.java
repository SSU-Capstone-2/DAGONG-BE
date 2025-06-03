package com.capstone2.capstone2.global.oauth.controller;

import com.capstone2.capstone2.domain.member.converter.MemberConverter;
import com.capstone2.capstone2.domain.member.dto.MemberResponseDTO.JoinResultDTO;
import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.global.oauth.dto.KakaoDTO;
import com.capstone2.capstone2.global.oauth.dto.KakaoTokenResponseDTO;
import com.capstone2.capstone2.global.util.KakaoUtil;
import com.capstone2.capstone2.global.oauth.service.KakaoTokenService;
import com.capstone2.capstone2.global.util.JwtUtil;
import com.capstone2.capstone2.global.common.response.ApiResponse;
import com.capstone2.capstone2.global.error.code.status.SuccessStatus;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class KakaoTestController {

    private final KakaoUtil kakaoUtil;
    private final KakaoTokenService kakaoTokenService;
    private final JwtUtil jwtUtil;

    /**
     *  - 카카오 개발자 콘솔에 반드시
     *      http://localhost:8080/auth/login/kakao-test
     *    을 Redirect URI로 등록해 두셔야 합니다.
     *
     *  - 브라우저에서
     *      https://kauth.kakao.com/oauth/authorize?
     *          response_type=code
     *          &client_id={REST_API_KEY}
     *          &redirect_uri=http://localhost:8080/auth/login/kakao-test
     *    로 접속 → 로그인·동의 → 자동으로 이 메서드가 실행됩니다.
     */
//    @GetMapping("/auth/login/kakao-test")
//    public ApiResponse<JoinResultDTO> kakaoTestLogin(
//            @RequestParam("code") String code,
//            HttpServletResponse response
//    ) {
//        // 1) 카카오 서버에 인가코드로 Access/Refresh 토큰 교환
//        KakaoDTO.OAuthToken oAuthToken = kakaoUtil.requestToken(code);
//
//        // 2) 교환된 Access Token으로 프로필 조회
//        KakaoDTO.KakaoProfile kakaoProfile = kakaoUtil.requestProfile(oAuthToken);
//
//        // 3) 프로필 정보로 DB에 회원이 있는지 확인 후 로그인(또는 가입)
//        //    여기서 KakaoTokenService가 이메일 혹은 kakaoId 기준으로 조회·생성해 줍니다.
//        //    반환된 Member에 이메일·닉네임·프로필URL 등이 들어있습니다.
//        KakaoTokenResponseDTO.TokenAndProfile kakaoData = new KakaoTokenResponseDTO.TokenAndProfile(
//                oAuthToken.getAccess_token(),
//                oAuthToken.getRefresh_token(),
//                (long) oAuthToken.getExpires_in(),
//                (long) oAuthToken.getRefresh_token_expires_in(),
//                kakaoProfile.getId(),
//                kakaoProfile.getKakao_account().getEmail(),
//                kakaoProfile.getKakao_account().getProfile().getProfile_image_url(),
//                kakaoProfile.getProperties().getNickname()
//        );
//        Member member = kakaoTokenService.loginOrJoinWithKakaoData(kakaoData, response);
//
//        // 4) 자체 JWT 생성 후 헤더에 실어주기
//        String jwt = jwtUtil.createAccessToken(member.getEmail());
//        response.setHeader("Authorization", "Bearer " + jwt);
//
//        // 5) body에도 token을 포함해 리턴 (Swagger에서도 바로 확인 가능)
//        JoinResultDTO dto = MemberConverter.toJoinResultDTO_WithToken(member, jwt);
//        return ApiResponse.onSuccess(SuccessStatus.USER_EMAIL_LOGIN_OK, dto);
//    }
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

        // (선택) JWT를 header에도 담아 둘 수 있지만, 리다이렉트 시 화면에 노출하려면 URL에 붙여주는 편
        // response.setHeader("Authorization", "Bearer " + jwt);

        // 5) 인가 코드 소진 문제 방지를 위해, 성공 페이지로 리다이렉트
        //    브라우저가 아래 주소(/kakao-success?token=…​)로 자동으로 이동하게 됨
        String redirectUrl = "/kakao-success?token=" + jwt;
        response.sendRedirect(redirectUrl);
    }

}

