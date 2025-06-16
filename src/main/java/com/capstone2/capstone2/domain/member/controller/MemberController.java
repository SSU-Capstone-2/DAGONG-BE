package com.capstone2.capstone2.domain.member.controller;

import com.capstone2.capstone2.domain.member.dto.*;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @Operation(summary = "회원 관심 카테고리 설정", description = "최대 5개까지 관심 카테고리를 등록 / 서브 카테고리 null 입력 가능")
    @PostMapping("/{memberId}/categories")
    public ApiResponse<List<MemberCategoryResponseDTO>> updateCategories(
            @PathVariable Long memberId,
            @RequestBody List<MemberCategoryRequestDTO> reqList) {
        Member member = authService.getLoginUser();
        List<MemberCategoryResponseDTO> info =
                memberService.updateCategories(memberId, reqList);

        return ApiResponse.onSuccess(SuccessStatus.CATEGORY_UPDATE_OK, info);
    }

    @Operation(summary = "찜 설정", description = "멤버 id와 공구 id를 입력하면 찜 설정이 됩니다.")
    @PostMapping("/{groupPurchaseId}/likes")
    public ApiResponse<MemberItemLikeResponseDto> like(
            @PathVariable("groupPurchaseId") Long groupPurchaseId,
            @RequestParam Long memberId) {
        Member member = authService.getLoginUser();
        MemberItemLikeResponseDto dto = memberService.like(memberId, groupPurchaseId);
        return ApiResponse.onSuccess(SuccessStatus.LIKE_SUCCESS, dto);
    }

    @Operation(summary = "찜 삭제", description = "멤버 id와 공구 id를 입력하면 찜이 해제 됩니다.")
    @DeleteMapping("/{groupPurchaseId}/likes")
    public ApiResponse<MemberItemLikeResponseDto> unlike(
            @PathVariable("groupPurchaseId") Long gpId,
            @RequestParam Long memberId) {
        Member member = authService.getLoginUser();
        MemberItemLikeResponseDto dto = memberService.unlike(memberId, gpId);
        return ApiResponse.onSuccess(SuccessStatus.UNLIKE_SUCCESS, dto);
    }

    @Operation(summary = "찜 조회", description = "멤버 id를 입력하면 찜을 누른 순서대로 목록이 반환됩니다.")
    @GetMapping("/members/{memberId}/likes")
    public ApiResponse<List<MemberLikedGroupPurchaseDto>> getLikedGroupPurchases(
            @PathVariable Long memberId) {
        Member member = authService.getLoginUser();
        List<MemberLikedGroupPurchaseDto> list = memberService.findLikedGroupPurchases(memberId);
        return ApiResponse.onSuccess(
                SuccessStatus.LIKE_LIST_SUCCESS,
                list
        );
    }


}
