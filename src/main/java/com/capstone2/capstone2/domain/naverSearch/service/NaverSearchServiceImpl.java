package com.capstone2.capstone2.domain.naverSearch.service;

import com.capstone2.capstone2.domain.naverSearch.converter.NaverSearchConverter;
import com.capstone2.capstone2.domain.naverSearch.dto.NaverSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class NaverSearchServiceImpl implements NaverSearchService {

    private final RestClient restSearchClient;

    @Value("${naver.search.client-id}")
    private String clientId;

    @Value("${naver.search.client-secret}")
    private String clientSecret;

    public NaverSearchResponse.SimpleResponseDTO search(String query, int page, int size) {
        int start = (page - 1) * size + 1;

        NaverSearchResponse.FullResponseDTO response = restSearchClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/search/shop.json")
                        .queryParam("query", query)
                        .queryParam("display", size)
                        .queryParam("start", start)
                        .queryParam("sort","sim")
                        .build())
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .retrieve()
                .body(NaverSearchResponse.FullResponseDTO.class);

        return NaverSearchConverter.toSimpleResponseDTO(response);
    }
}
