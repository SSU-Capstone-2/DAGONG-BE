package com.capstone2.capstone2.domain.naverSearch.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NaverSearchSimpleResponseDTO {
    private int totalCount;
    private int page;
    private int pageSize;
    private List<NaverSearchItemDTO> items;
}
