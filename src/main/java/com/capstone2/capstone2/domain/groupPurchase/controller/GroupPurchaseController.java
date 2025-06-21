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
import io.swagger.v3.oas.annotations.Parameters;
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
    @Operation(summary = "공동구매 전체 목록 조회", description = "공동구매 전체 목록을 조회하는 API입니다. 페이지는 1부터 시작합니다.")
    @Parameter(name = "memberId", description = "공구를 등록하는 멤버의 ID, 추후 hidden으로 수정 예정입니다.")
    @GetMapping("/{memberId}")
    public ApiResponse<Page<GroupPurchaseResponse.GroupPurchaseListDTO>> getAllGroupPurchases(
        @PathVariable("memberId") Long memberId,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ){

        Page<GroupPurchaseResponse.GroupPurchaseListDTO> response = groupPurchaseService.getAllPurchases(memberId, page, size);
        return ApiResponse.onSuccess(SuccessStatus.GROUP_PURCHASE_FETCH_ALL_OK, response);
    }

    // 공구 상세 조회 API
    @Operation(summary = "공동구매 상세 조회", description = "공동구매를 상세 조회하는 API입니다.")
    @Parameter(name = "groupPurchaseId", description = "상세 조회할 공동구매의 ID입니다.")
    @GetMapping("detail/{groupPurchaseId}")
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

    // 인기 공구 목록 조회 API
    @GetMapping("/ranking")
    @Operation(summary = "인기 공구 목록 조회", description = "조회수 또는 찜 수 기준으로 전체 공구 목록을 반환합니다. Parameter로 views를 주면 조회수, likes를 주면 찜 수를 기반으로 정렬합니다. 페이지는 1부터 시작합니다.")
    public ApiResponse<Page<GroupPurchaseResponse.GroupPurchaseListDTO>> getPopularGroupPurchases(
            @RequestParam(defaultValue = "views") String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
       Page<GroupPurchaseResponse.GroupPurchaseListDTO> response =
               groupPurchaseService.getPopularGroupPurchases(sort, page, size);
       return ApiResponse.onSuccess(SuccessStatus.GROUP_PURCHASE_FETCH_RANKING_OK, response);
    }

    // 특정 카테고리의 공구 목록 조회 API
    @GetMapping("/category")
    @Operation(summary = "카테고리별 공구 목록 조회", description = "대분류(category1), 소분류(cateogry2)를 기준으로 공구 목록을 조회하는 API입니다. 페이지는 1부터 시작합니다.")
    public ApiResponse<Page<GroupPurchaseResponse.GroupPurchaseListDTO>> getGroupPurchaseByCategory(
            @RequestParam String category1,
            @RequestParam(required = false) String category2,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<GroupPurchaseResponse.GroupPurchaseListDTO> response =
                groupPurchaseService.getGroupPurchasesByCategory(category1, category2, page, size);
        return ApiResponse.onSuccess(SuccessStatus.GROUP_PURCHASE_FETCH_BY_CATEGORY, response);
    }

    // 공구 참여 API
    @Operation(summary = "공동 구매 참여", description = "특정 공동 구매에 사용자가 참여하는 API 입니다.")
    @Parameters({
            @Parameter(name = "groupPurchaseId", description = "사용자가 참여하고자 하는 공구의 ID 입니다", required = true),
            @Parameter(name = "memberId", description = "공동 구매 참여자의 ID입니다. 추후 hidden으로 수정 예정입니다.", required = true)
    })
    @PostMapping("/participate/{groupPurchaseId}/{memberId}")
    public ApiResponse<GroupPurchaseResponse.GroupPurchaseIdDTO> participateGroupPurchase(
            @PathVariable("groupPurchaseId") Long groupPurchaseId,
            @PathVariable("memberId") Long memberId
    ){
        GroupPurchase groupPurchase = groupPurchaseService.participateGroupPurchase(groupPurchaseId, memberId);
        GroupPurchaseResponse.GroupPurchaseIdDTO response = GroupPurchaseConverter.toGroupPurchaseIdDTO(groupPurchase);
        return ApiResponse.onSuccess(SuccessStatus.GROUP_PURCHASE_PARTICIPATE_OK, response);
    }

    // 공구 참여 취소 API
    @Operation(summary = "공동 구매 참여 취소", description = "특정 공동 구매에 대한 사용자의 참여를 취소하는 API 입니다.")
    @Parameters({
            @Parameter(name = "groupPurchaseId", description = "사용자가 참여를 취소하고자 하는 공구의 ID 입니다.", required = true),
            @Parameter(name = "memberId", description = "공동 구매 참여를 취소하고자 하는 사용자의 ID 입니다. 추후 hidden으로 수정 예정입니다.")
    })
    @DeleteMapping("/participate/{groupPurchaseId}/{memberId}")
    public ApiResponse<GroupPurchaseResponse.GroupPurchaseIdDTO> cancelGroupPurchaseParticipation(
            @PathVariable("groupPurchaseId") Long groupPurchaseId,
            @PathVariable("memberId") Long memberId
    ){
        GroupPurchase groupPurchase = groupPurchaseService.cancelGroupPurchaseParticipate(groupPurchaseId, memberId);
        GroupPurchaseResponse.GroupPurchaseIdDTO response = GroupPurchaseConverter.toGroupPurchaseIdDTO(groupPurchase);
        return ApiResponse.onSuccess(SuccessStatus.GROUP_PURCHASE_PARTICIPATE_CANCEL_OK, response);
    }

    @Operation(
            summary = "공구 검색 (품목명, 지역, 카테고리, 정렬, 페이징)",
            description = "itemName: 검색어, category: 대분류 카테고리 (선택), sort: views|latest|oldest|likes (default=latest), "
                    + "page:페이지번호(default=1), size:페이지크기(default=10), memberId:조회자의 ID"
    )
    @GetMapping("/search/items")
    public ApiResponse<Page<GroupPurchaseResponse.GroupPurchaseListDTO>> searchItems(
            @RequestParam("memberId") Long memberId,
            @RequestParam("itemName") String itemName,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "sort",    defaultValue = "latest") String sort,
            @RequestParam(value = "page",    defaultValue = "1")      int page,
            @RequestParam(value = "size",    defaultValue = "10")     int size
    ) {
        Page<GroupPurchaseResponse.GroupPurchaseListDTO> pageResult
                = groupPurchaseService.searchGroupPurchases(
                memberId, itemName, category, sort, page, size);

        return ApiResponse.onSuccess(
                SuccessStatus.GROUP_PURCHASE_SEARCH_OK,
                pageResult
        );
    }

    
    // 내가 올린 공구 API
    @Operation(summary = "내가 등록한 공구 목록", description = "사용자가 등록한 공구를 최신순으로 페이징 조회하는 API입니다.")
    @Parameters(
            @Parameter(name = "memberId", description = "멤버 ID")
    )
    @GetMapping("/mine/{memberId}")
    public ApiResponse<Page<GroupPurchaseResponse.GroupPurchaseListDTO>> getMyGroupPurchases(
            @PathVariable("memberId") Long memberId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<GroupPurchaseResponse.GroupPurchaseListDTO> result =
                groupPurchaseService.getMyGroupPurchases(memberId, page, size);

        return ApiResponse.onSuccess(SuccessStatus.GROUP_PURCHASE_MINE_OK, result);
    }

    // 내가 참여중인 공구 목록 조회 API
    @Operation(summary = "내가 참여중인 공구 목록", description = "사용자가 참여자로 속해 있는 공구를 최신순으로 페이징 조회하는 API입니다.")
    @Parameters(
            @Parameter(name = "memberId", description = "멤버 ID")
    )
    @GetMapping("/participate/{memberId}")
    public ApiResponse<Page<GroupPurchaseResponse.GroupPurchaseListDTO>> getParticipatingPurchases(
            @PathVariable("memberId") Long memberId,
            @RequestParam(defaultValue =  "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<GroupPurchaseResponse.GroupPurchaseListDTO> result =
                groupPurchaseService.getParticipatingPurchases(memberId, page, size);


        return ApiResponse.onSuccess(SuccessStatus.GROUP_PURCHASE_PARTICIPATING_OK, result);
    }
}