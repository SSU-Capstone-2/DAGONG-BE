package com.capstone2.capstone2.domain.naverSearch.client;

import com.capstone2.capstone2.domain.naverSearch.converter.NaverSearchConverter;
import com.capstone2.capstone2.domain.naverSearch.dto.NaverSearchResponseDTO;
import com.capstone2.capstone2.domain.naverSearch.dto.NaverSearchSimpleResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class NaverSearchClient {

    private final RestClient restSearchClient;

    @Value("${naver.search.client-id}")
    private String clientId;

    @Value("${naver.search.client-secret}")
    private String clientSecret;

    public NaverSearchSimpleResponseDTO search(String query, int page, int size) {
        int start = (page - 1) * size + 1;

        NaverSearchResponseDTO response = restSearchClient.get()
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
                .body(NaverSearchResponseDTO.class);


        return NaverSearchConverter.toSimpleResponse(response, page, size);

    }
}
