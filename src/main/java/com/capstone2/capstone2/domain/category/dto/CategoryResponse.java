package com.capstone2.capstone2.domain.category.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

public class CategoryResponse {

    @Data
    @Builder
    public static class MainCategoryDTO {
        private Long id;
        private String name;
        private List<SubCategoryDTO> subCategories;
    }

    @Data
    @Builder
    public static class SubCategoryDTO {
        private Long id;
        private String name;
    }

}
