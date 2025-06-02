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

    // 공구 상세 조회 API
    @Operation(summary = "공동구매 상세 조회", description = "공동구매를 상세 조회하는 API입니다.")
    @Parameter(name = "groupPurchaseId", description = "상세 조회할 공동구매의 ID입니다.")
    @GetMapping("/{groupPurchaseId}")
    public ApiResponse<GroupPurchaseResponse.GroupPurchaseDetailDTO> getGroupPurchaseDetail(
            @PathVariable("groupPurchaseId") Long groupPurchaseId
    ){
        GroupPurchaseResponse.GroupPurchaseDetailDTO response = groupPurchaseService.getGroupPurchaseDetail(groupPurchaseId);
        return ApiResponse.onSuccess(SuccessStatus.GROUP_PURCHASE_FETCH_DETAIL_OK, response);
    }

    // 공구 정보 수정 API
    @Operation(summary = "공동구매 정보 수정", description = "공동구매 정보를 수정하는 API 입니다.")
    @Parameter(name = "groupPurchaseId", description = "정보를 수정할 공동구매의 ID입니다.")
    @PatchMapping("/{groupPurchaseId}")
    public ApiResponse<GroupPurchaseResponse.GroupPurchaseIdDTO> updateGroupPurchase(
        @PathVariable("groupPurchaseId") Long groupPurchaseId,
        @RequestBody GroupPurchaseRequest.GroupPurchaseUpdateDTO request
    ){
        GroupPurchase groupPurchase = groupPurchaseService.updateGroupPurchase(groupPurchaseId, request);
        GroupPurchaseResponse.GroupPurchaseIdDTO response = GroupPurchaseConverter.toGroupPurchaseIdDTO(groupPurchase);
        return ApiResponse.onSuccess(SuccessStatus.GROUP_PURCHASE_UPDATE_OK, response);
    }

    // 공구 삭제 API
    @Operation(summary = "공동구매 삭제", description = "공동구매를 삭제하는 API 입니다.")
    @Parameter(name = "groupPurchaseId", description = "삭제할 공동구매의 ID입니다.")
    @DeleteMapping("/{groupPurchaseId}")
    public ApiResponse<String> deleteGroupPurchase(
        @PathVariable("groupPurchaseId") Long groupPurchaseId
    ){
        groupPurchaseService.deleteGroupPurchase(groupPurchaseId);
        return ApiResponse.onSuccess(SuccessStatus.GROUP_PURCHASE_DELETE_OK, groupPurchaseId + "번 공동구매 삭제 성공");
    }
}
