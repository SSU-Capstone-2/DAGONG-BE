package com.capstone2.capstone2.domain.naverSearch.controller;

import com.capstone2.capstone2.domain.naverSearch.dto.NaverSearchSimpleResponseDTO;
import com.capstone2.capstone2.domain.naverSearch.client.NaverSearchClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "네이버 검색 API", description = "네이버 검색 API 입니다.")
public class NaverSearchController {

    private final NaverSearchClient naverSearchClient;

    @Operation(summary = "네이버 쇼핑 검색", description = "품목명을 기준으로 네이버 쇼핑 검색 API를 통해 검색 결과를 반환합니다.")
    @Parameters({
            @Parameter(name = "query", description = "검색어", required = true),
            @Parameter(name = "page", description = "페이지 번호 (1부터 시작)", required = true),
            @Parameter(name = "size", description = "페이지 당 결과 수", required = true)
    })
    @GetMapping("/search")
    public NaverSearchSimpleResponseDTO search(
            @RequestParam String query,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        return naverSearchClient.search(query, page, size);
    }
}
