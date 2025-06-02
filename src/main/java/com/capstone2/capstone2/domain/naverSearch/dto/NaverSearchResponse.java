package com.capstone2.capstone2.domain.naverSearch.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

public class NaverSearchResponse {
    @Data
    @Builder
    public static class ItemDTO {
        private String title;
        private String link;
        private String image;
        private int lprice;
        private String brand;
        private String category1;
        private String category2;
    }

    @Data
    @Builder
    public static class FullResponseDTO {
        private Integer total; // 전체 검색 결과 수
        private Integer start; // 현재 페이지 번호
        private Integer display; // 한 페이지당 항목 수
        private List<ItemDTO> items; // 결과 리스트
    }

    @Data
    @Builder
    public static class SimpleResponseDTO {
        private Integer totalCount;
        private Integer page;
        private Integer pageSize;
        private List<ItemDTO> items;
    }
}
