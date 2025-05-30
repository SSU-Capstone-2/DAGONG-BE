package com.capstone2.capstone2.domain.naverSearch.controller;

import com.capstone2.capstone2.domain.naverSearch.dto.NaverSearchResponse;
import com.capstone2.capstone2.domain.naverSearch.service.NaverSearchService;
import com.capstone2.capstone2.global.common.response.ApiResponse;
import com.capstone2.capstone2.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "네이버 검색 API", description = "네이버 검색 API 입니다.")
public class NaverSearchController {

    private final NaverSearchService naverSearchService;

    @Operation(summary = "네이버 쇼핑 검색", description = "품목명을 기준으로 네이버 쇼핑 검색 API를 통해 검색 결과를 반환합니다.")
    @Parameters({
            @Parameter(name = "query", description = "검색어", required = true),
            @Parameter(name = "page", description = "페이지 번호 (1부터 시작)", required = true),
            @Parameter(name = "size", description = "페이지 당 결과 수", required = true)
    })
    @GetMapping("/search")
    public ApiResponse<NaverSearchResponse.SimpleResponseDTO> search(
            @RequestParam String query,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        NaverSearchResponse.SimpleResponseDTO response = naverSearchService.search(query, page, size);
        return ApiResponse.onSuccess(SuccessStatus.NAVER_SEARCH_FETCH_OK, response);
    }
}
