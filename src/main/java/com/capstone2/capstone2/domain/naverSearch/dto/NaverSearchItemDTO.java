package com.capstone2.capstone2.domain.naverSearch.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NaverSearchItemDTO {
    private String title; // 상품 이름
    private String link; // 상품 정보 URL
    private String image; // 상품 이미지 URL
    private int lprice; // 상품 가격
    private String brand; // 상품 브랜드
    private String category1; // 상품 카테고리 대분류
    private String category2; // 상품 카테고리 중분류
}
