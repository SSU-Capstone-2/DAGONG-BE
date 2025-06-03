package com.capstone2.capstone2.global.oauth.controller;

import com.capstone2.capstone2.domain.member.converter.MemberConverter;
import com.capstone2.capstone2.domain.member.dto.MemberResponseDTO;
import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.global.common.response.ApiResponse;
import com.capstone2.capstone2.global.error.code.status.SuccessStatus;
import com.capstone2.capstone2.global.oauth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "KAKAO API", description = "KAKAO API 입니다.")
public class AuthController {

    private final AuthService authService;


    @GetMapping("/login/kakao")
    @Operation(summary = "kakao login API", description = "kakao login용 API 입니다.")
    public ApiResponse<MemberResponseDTO.JoinResultDTO> kakaoLogin(@RequestParam("code") String accessCode, HttpServletResponse httpServletResponse) {
        System.out.println("🔥 kakaoLogin 실행됨");  // 이게 안 보이면 요청이 안 들어온 것
        Member member = authService.oAuthLogin(accessCode, httpServletResponse);
        return ApiResponse.onSuccess(SuccessStatus.USER_EMAIL_LOGIN_OK, MemberConverter.toJoinResultDTO(member));
    }
}
