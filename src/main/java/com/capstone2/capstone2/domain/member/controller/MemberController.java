package com.capstone2.capstone2.domain.member.controller;

import com.capstone2.capstone2.domain.member.dto.MemberResponseDTO;
import com.capstone2.capstone2.domain.member.service.MemberService;
import com.capstone2.capstone2.global.common.response.ApiResponse;
import com.capstone2.capstone2.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "멤버 API", description = "멤버 API입니다.")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "회원 정보 조회", description = "ID로 회원의 모든 정보를 조회합니다.")
    @Parameter(name = "id", description = "조회할 회원의 ID", required = true)
    @GetMapping("/{id}")
    public ApiResponse<MemberResponseDTO.InfoDTO> getMemberInfo(@PathVariable Long id) {
        MemberResponseDTO.InfoDTO info = memberService.getMemberInfo(id);
        return ApiResponse.onSuccess(SuccessStatus.GET_MEMBER_SUCCESS, info);
    }

}
