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
import com.capstone2.capstone2.global.oauth.service.KakaoLoginService;
import com.capstone2.capstone2.global.util.KakaoUtil;
import com.capstone2.capstone2.global.oauth.service.KakaoTokenService;
import com.capstone2.capstone2.global.util.JwtUtil;
import com.capstone2.capstone2.global.common.response.ApiResponse;
import com.capstone2.capstone2.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "KAKAO TEST API", description = "KAKAO TEST용 API 입니다.")
public class KakaoTestController {

    private final AuthService authService;
    private final KakaoLoginService kakaoLoginService;

    @GetMapping("/login/kakao-test")
    public void kakaoTestLogin(
            @RequestParam("code") String code,
            HttpServletResponse response
    ) throws IOException {
        kakaoLoginService.kakaoTestLogin(code, response);
    }

    @GetMapping("/user")
    public ApiResponse<JoinResultDTO> getLoginUser() {
        Member member = authService.getLoginUser();
        JoinResultDTO dto = authService.toCurrentUserDto(member);
        return ApiResponse.onSuccess(SuccessStatus.KAKAO_USER_FETCH_OK, dto);
    }
}