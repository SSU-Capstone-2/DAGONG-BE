package com.capstone2.capstone2.domain.category.converter;

import com.capstone2.capstone2.domain.category.dto.CategoryResponse;
import com.capstone2.capstone2.domain.category.entity.MainCategory;
import com.capstone2.capstone2.domain.category.entity.SubCategory;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryConverter {

    public static CategoryResponse.SubCategoryDTO toSubCategoryDTO(SubCategory sub) {
        return CategoryResponse.SubCategoryDTO.builder()
                .id(sub.getId())
                .name(sub.getName())
                .build();
    }

    public static CategoryResponse.MainCategoryDTO toMainCategoryDTO(MainCategory main, List<SubCategory> subs) {
        List<CategoryResponse.SubCategoryDTO> subDTOs = subs.stream()
                .map(CategoryConverter::toSubCategoryDTO)
                .collect(Collectors.toList());

        return CategoryResponse.MainCategoryDTO.builder()
                .id(main.getId())
                .name(main.getName())
                .subCategories(subDTOs)
                .build();
    }
}
