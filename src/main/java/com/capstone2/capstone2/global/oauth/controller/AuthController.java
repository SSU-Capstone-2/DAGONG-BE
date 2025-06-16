package com.capstone2.capstone2.global.oauth.controller;

import com.capstone2.capstone2.domain.member.converter.MemberConverter;
import com.capstone2.capstone2.domain.member.dto.MemberResponseDTO;
import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.global.common.response.ApiResponse;
import com.capstone2.capstone2.global.error.code.status.SuccessStatus;
import com.capstone2.capstone2.global.oauth.dto.LoginResponseDTO;
import com.capstone2.capstone2.global.oauth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "KAKAO API", description = "KAKAO API 입니다.")
public class AuthController {

    private final AuthService authService;

    @Value("${app.front.redirect-url}")
    private String frontRedirectUrl;


    @GetMapping("/login/kakao")
    @Operation(summary = "kakao login (JSON)", description = "인가 코드 처리 후 JSON으로 토큰·유저 정보를 반환하고, 클라이언트에서 라우터 이동을 제어합니다.")
    public ApiResponse<LoginResponseDTO> kakaoLoginJson(
            @RequestParam("code") String accessCode, HttpServletResponse httpServletResponse) {
        Member member = authService.oAuthLogin(accessCode, httpServletResponse);
        String jwt = authService.createToken(member);
        MemberResponseDTO.JoinResultDTO userDto = MemberConverter.toJoinResultDTO(member);
        LoginResponseDTO responseDto = new LoginResponseDTO(jwt, userDto);
        return ApiResponse.onSuccess(SuccessStatus.USER_EMAIL_LOGIN_OK, responseDto);
    }


    @GetMapping("/login/kakao/redirect")
    @Operation(summary = "kakao login (Redirect)", description = "인가 코드 처리 후 302 리다이렉트 방식으로 클라이언트 라우트로 이동시킵니다.")
    public void kakaoLoginRedirect(
            @RequestParam("code") String accessCode,
            HttpServletResponse response) throws IOException {
        Member member = authService.oAuthLogin(accessCode, response);
        String jwt = authService.createToken(member);
        // 쿼리 파라미터에 token, userId 전달
        String redirectUri = String.format("%s?token=%s&userId=%d", frontRedirectUrl, jwt, member.getId());
        response.sendRedirect(redirectUri);
    }
}
