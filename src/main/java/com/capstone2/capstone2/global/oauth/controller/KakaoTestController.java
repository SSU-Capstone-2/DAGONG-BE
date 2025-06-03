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

//    private final MemberRepository memberRepository;
//
//    @Operation(
//            summary = "현재 로그인된 사용자 정보 조회",
//            security = @SecurityRequirement(name = "JWT TOKEN") // SwaggerConfig의 스키마 이름과 동일해야 합니다.
//    )
//    @GetMapping("/auth/user")
//    public ApiResponse<MemberResponseDTO.JoinResultDTO> getCurrentUser(HttpServletRequest request) {
////        String authorizationHeader = request.getHeader("Authorization");
////        // 1) "Bearer {token}"에서 실제 토큰 부분만 떼어내기
////        String token = null;
////        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
////            token = authorizationHeader.substring(7);
////        }
////
////        // 2) JWT 유효성 검사 (필요 시 예외 처리 로직 추가)
////        if (token == null || !jwtUtil.validateToken(token)) {
////            // 유효하지 않은 토큰
////            // 여기서 ApiResponse.onFail(...)로 리턴하거나, 필요 시 401 예외를 던집니다.
////            return null;
////        }
////
////        // 3) 토큰에서 추출한 사용자 식별 정보(이메일)을 꺼냄
////        String email = jwtUtil.getUserIdFromToken(token);
//        // ※ 주의: getUserIdFromToken()이 현재 이메일을 sub(claim)으로 사용한다고 가정
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        // (JwtFilter 단계에서 이미 authentication이 세팅된 상태라고 가정)
//
//        // 2) Authentication.getPrincipal() 또는 getName()으로 사용자 식별자(여기서는 이메일)를 가져옴
//        //    예시: JwtFilter에서 UsernamePasswordAuthenticationToken(email, authorities)를 set 했을 경우
//        String email = authentication.getName(); // 또는 (String)authentication.getPrincipal();
//
//        // 3) 회원 정보 조회
//        Member member = memberRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
//
//        // 5) Member 엔티티를 CurrentUserDTO로 변환
//        MemberResponseDTO.JoinResultDTO dto = MemberResponseDTO.JoinResultDTO.builder()
//                .id(member.getId())
//                .email(member.getEmail())
//                .nickname(member.getNickname())
//                .build();
//
//        // 6) ApiResponse 래핑하여 리턴
//        return ApiResponse.onSuccess(SuccessStatus.KAKAO_USER_FETCH_OK, dto);
//    }

}

