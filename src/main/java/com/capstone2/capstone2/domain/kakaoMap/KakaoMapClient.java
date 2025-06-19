package com.capstone2.capstone2.domain.kakaoMap;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class KakaoMapClient {

    // ① WebClient.Builder 빈 주입
    private final WebClient.Builder webClientBuilder;

    // ② application.yml 에서 kakao.client 로 읽어옴
    @Value("${kakao.client}")
    private String apiKey;

    /**
     * 위경도를 받아 카카오 API 호출 → "시도 구군 읍면동" 형태 문자열 리턴
     */
    public String reverseGeocode(double latitude, double longitude) {
        // 1) Builder 를 이용해 WebClient 인스턴스 생성
        WebClient client = webClientBuilder
                .baseUrl("https://dapi.kakao.com")
                .defaultHeader("Authorization", "KakaoAK " + apiKey)
                .build();

        // 2) 호출
        Map<String,Object> response = client.get()
                .uri(uri -> uri
                        .path("/v2/local/geo/coord2address.json")
                        .queryParam("x", longitude)
                        .queryParam("y", latitude)
                        .build