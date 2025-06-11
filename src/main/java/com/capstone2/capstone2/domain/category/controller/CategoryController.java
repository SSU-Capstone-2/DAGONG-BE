package com.capstone2.capstone2.domain.category.controller;

import com.capstone2.capstone2.domain.category.dto.CategoryResponse;
import com.capstone2.capstone2.domain.category.service.CategoryService;
import com.capstone2.capstone2.global.common.response.ApiResponse;
import com.capstone2.capstone2.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category", description = "카테고리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

//    @Operation(summary = "카테고리 목록 조회", description = "대분류와 중분류를 모두 조회합니다.")
//    @GetMapping
//    public ApiResponse<List<CategoryResponse.MainCategoryDTO>> getAllCategories() {
//        List<CategoryResponse.MainCategoryDTO> result = categoryService.getAllCategories();
//        return ApiResponse.onSuccess(SuccessStatus.CATEGORY_FETCH_ALL_OK, result);
//    }
//
//    @GetMapping("/main")
//    public ApiResponse<List<CategoryResponse.MainCategoryDTO>> getMainCategories() {
//        List<CategoryResponse.MainCategoryDTO> result = categoryService.getMainCategories();
//        return ApiResponse.onSuccess(SuccessStatus.CATEGORY_FETCH_MAIN_OK, result);
//    }
//
//    @GetMapping("/main/{mainCategoryId}/sub")
//    public ApiResponse<List<CategoryResponse.SubCategoryDTO>> getSubCategories(@PathVariable Long mainCategoryId) {
//        List<CategoryResponse.SubCategoryDTO> result = categoryService.getSubCategoriesByMainId(mainCategoryId);
//        return ApiResponse.onSuccess(SuccessStatus.CATEGORY_FETCH_SUB_OK, result);
//
//
//    }
}