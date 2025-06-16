package com.capstone2.capstone2.domain.category.service;

import com.capstone2.capstone2.domain.category.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse.MainCategoryDTO> getAllCategories();
    List<CategoryResponse.MainCategoryDTO> getMainCategories();
    List<CategoryResponse.SubCategoryDTO> getSubCategoriesByMainId(Long mainCategoryId);

}
