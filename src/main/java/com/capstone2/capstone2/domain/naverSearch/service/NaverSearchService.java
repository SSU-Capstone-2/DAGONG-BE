package com.capstone2.capstone2.domain.naverSearch.service;
import com.capstone2.capstone2.domain.naverSearch.dto.NaverSearchResponse;

public interface NaverSearchService {
    NaverSearchResponse.SimpleResponseDTO search(String query, int page, int size);
}
