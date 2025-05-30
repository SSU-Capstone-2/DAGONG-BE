package com.capstone2.capstone2.domain.naverSearch.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NaverSearchResponseDTO {
    private int total; // 전체 검색 결과 수
    private int start; // 현재 페이지 번호
    private int display; // 한 페이지당 항목 수
    private List<NaverSearchItemDTO> items; // 결과 리스트
}
