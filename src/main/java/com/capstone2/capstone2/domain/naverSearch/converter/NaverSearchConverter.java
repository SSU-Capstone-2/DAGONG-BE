package com.capstone2.capstone2.domain.naverSearch.converter;

import com.capstone2.capstone2.domain.naverSearch.dto.NaverSearchResponse;

import java.util.List;
import java.util.stream.Collectors;

public class NaverSearchConverter {

    private static String removeHtmlTags(String input) {
        return input == null ? null : input.replaceAll("<[^>]*>", "");
    }

    // NaverSearchConverter.toSimpleResponseDTO(response, page, size)
    public static NaverSearchResponse.SimpleResponseDTO toSimpleResponseDTO(NaverSearchResponse.FullResponseDTO response, int page, int size) {
        List<NaverSearchResponse.ItemDTO> items = response.getItems().stream()
                .map(item -> NaverSearchResponse.ItemDTO.builder()
                        .title(removeHtmlTags(item.getTitle()))
                        .link(item.getLink())
                        .image(item.getImage())
                        .lprice(item.getLprice())
                        .brand(item.getBrand())
                        .category1(item.getCategory1())
                        .category2(item.getCategory2())
                        .build())
                .collect(Collectors.toList());

        return NaverSearchResponse.SimpleResponseDTO.builder()
                .totalCount(response.getTotal())
                .page(page)
                .pageSize(size)
                .items(items)
                .build();
    }
}
