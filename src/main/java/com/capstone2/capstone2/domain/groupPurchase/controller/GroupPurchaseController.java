package com.capstone2.capstone2.domain.groupPurchase.controller;

import com.capstone2.capstone2.domain.groupPurchase.converter.GroupPurchaseConverter;
import com.capstone2.capstone2.domain.groupPurchase.dto.GroupPurchaseRequest;
import com.capstone2.capstone2.domain.groupPurchase.dto.GroupPurchaseResponse;
import com.capstone2.capstone2.domain.groupPurchase.entity.GroupPurchase;
import com.capstone2.capstone2.domain.groupPurchase.service.GroupPurchaseService;
import com.capstone2.capstone2.global.common.response.ApiResponse;
import com.capstone2.capstone2.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchases")
@Tag(name = "공동구매 API", description = "공동구매 API 입니다.")
public class GroupPurchaseController {

    private final GroupPurchaseService groupPurchaseService;

    // 공구 생성 API
    @Operation(summary = "공동구매 등록", description = "공동구매를 등록하는 API입니다.")
    @Parameter(name = "memberId", description = "공구를 등록하는 멤버의 ID, 추후 hidden으로 수정 예정입니다.")
    @PostMapping("/{memberId}")
    public ApiResponse<GroupPurchaseResponse.GroupPurchaseIdDTO> createGroupPurchase(
            @PathVariable("memberId") Long memberId,
            @RequestBody GroupPurchaseRequest.GroupPurchaseCreateDTO request) {
        GroupPurchase groupPurchase = groupPurchaseService.createGroupPurchase(memberId, request);
        GroupPurchaseResponse.GroupPurchaseIdDTO response = GroupPurchaseConverter.toGroupPurchaseIdDTO(groupPurchase);
        return ApiResponse.onSuccess(SuccessStatus.GROUP_PURCHASE_CREATE_OK, response);
    }

    // 공구 전체 목록 조회 API
    @Operation(summary = "공동구매 전체 목록 조회", description = "공동구매 전체 목록을 조회하는 API입니다. 페이징이 적용되어있고 페이지는 1부터 시작합니다.")
    @GetMapping
    public ApiResponse<Page<GroupPurchaseResponse.GroupPurchaseListDTO>> getAllGroupPurchases(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ){

        Page<GroupPurchaseResponse.GroupPurchaseListDTO> response = groupPurchaseService.getAllPurchases(page, size);
        return ApiResponse.onSuccess(SuccessStatus.GROUP_PURCHASE_FETCH_ALL_OK, response);
    }
}
