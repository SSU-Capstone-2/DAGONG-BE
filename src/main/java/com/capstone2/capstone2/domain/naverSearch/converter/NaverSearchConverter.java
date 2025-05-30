package com.capstone2.capstone2.domain.naverSearch.converter;

import com.capstone2.capstone2.domain.naverSearch.dto.NaverSearchItemDTO;
import com.capstone2.capstone2.domain.naverSearch.dto.NaverSearchResponseDTO;
import com.capstone2.capstone2.domain.naverSearch.dto.NaverSearchSimpleResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class NaverSearchConverter {

    private static String removeHtmlTags(String input) {
        return input == null ? null : input.replaceAll("<[^>]*>", "");
    }

    public static NaverSearchSimpleResponseDTO toSimpleResponse(NaverSearchResponseDTO response, int page, int size) {
        List<NaverSearchItemDTO> items = response.getItems().stream()
                .map(item -> NaverSearchItemDTO.builder()
                        .title(removeHtmlTags(item.getTitle()))
                        .link(item.getLink())
                        .image(item.getImage())
                        .lprice(item.getLprice())
                        .brand(item.getBrand())
                        .category1(item.getCategory1())
                        .category2(item.getCategory2())
                        .build())
                .collect(Collectors.toList());

        return NaverSearchSimpleResponseDTO.builder()
                .totalCount(response.getTotal())
                .page(page)
                .pageSize(size)
                .items(items)
                .build();
    }
}
