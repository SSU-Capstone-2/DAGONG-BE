package com.capstone2.capstone2.domain.member.controller;

import com.capstone2.capstone2.domain.member.dto.MemberRequestDTO;
import com.capstone2.capstone2.domain.member.dto.MemberResponseDTO;
import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.member.service.MemberService;
import com.capstone2.capstone2.global.common.response.ApiResponse;
import com.capstone2.capstone2.global.error.code.status.SuccessStatus;
import com.capstone2.capstone2.global.oauth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "멤버 API", description = "멤버 API입니다.")
public class MemberController {
    private final MemberService memberService;
    private final AuthService authService;

    @Operation(summary = "회원 정보 조회", description = "ID로 회원의 모든 정보를 조회합니다.")
    @Parameter(name = "id", description = "조회할 회원의 ID", required = true)
    @GetMapping("/{id}")
    public ApiResponse<MemberResponseDTO.InfoDTO> getMemberInfo(@PathVariable Long id) {
        Member member = authService.getLoginUser();
        MemberResponseDTO.InfoDTO info = memberService.getMemberInfo(id);
        return ApiResponse.onSuccess(SuccessStatus.GET_MEMBER_SUCCESS, info);
    }

    @Operation(summary = "회원 닉네임 수정", description = "ID로 회원을 찾아 닉네임만 수정합니다.")
    @PatchMapping("/{id}")
    public ApiResponse<MemberResponseDTO.InfoDTO> updateNickname(
            @Parameter(description = "수정할 회원의 ID") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "수정할 닉네임 정보", required = true
            )
            @Valid @RequestBody MemberRequestDTO request) {
        Member member = authService.getLoginUser();
        MemberResponseDTO.InfoDTO updated = memberService.updateNickname(id, request.getNickname());
        return ApiResponse.onSuccess(SuccessStatus.MEMBER_UPDATE_OK, updated);
    }

    @Operation(summary = "회원 탈퇴", description = "ID로 회원을 찾아 탈퇴 처리합니다.")
    @Parameter(name = "id", description = "탈퇴할 회원의 ID", required = true)
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteMember(
            @PathVariable Long id
    ) {
        Member member = authService.getLoginUser();
        memberService.deleteMember(id);
        return ApiResponse.onSuccess(SuccessStatus.MEMBER_DELETE_OK, null);
    }

}
