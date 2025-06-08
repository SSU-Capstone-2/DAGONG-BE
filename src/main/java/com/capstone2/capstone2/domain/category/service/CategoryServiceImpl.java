package com.capstone2.capstone2.domain.category.service;

import com.capstone2.capstone2.domain.category.converter.CategoryConverter;
import com.capstone2.capstone2.domain.category.dto.CategoryResponse;
import com.capstone2.capstone2.domain.category.entity.MainCategory;
import com.capstone2.capstone2.domain.category.entity.SubCategory;
import com.capstone2.capstone2.domain.category.handler.CategoryHandler;
import com.capstone2.capstone2.domain.category.repository.MainCategoryRepository;
import com.capstone2.capstone2.domain.category.repository.SubCategoryRepository;
import com.capstone2.capstone2.domain.groupPurchase.handler.GroupPurchaseHandler;
import com.capstone2.capstone2.global.error.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final MainCategoryRepository mainCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    @Override
    public List<CategoryResponse.MainCategoryDTO> getAllCategories() {
        List<MainCategory> mains = mainCategoryRepository.findAll();
        return mains.stream()
                .map(main -> {
                    List<SubCategory> subs = subCategoryRepository.findAllByMainCategory(main);
                    return CategoryConverter.toMainCategoryDTO(main, subs);
                })
                .collect(Collectors.toList());
    }
    @Override
    public List<CategoryResponse.MainCategoryDTO> getMainCategories() {
        return mainCategoryRepository.findAll().stream()
                .map(main -> CategoryResponse.MainCategoryDTO.builder()
                        .id(main.getId())
                        .name(main.getName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryResponse.SubCategoryDTO> getSubCategoriesByMainId(Long mainCategoryId) {
        MainCategory main = mainCategoryRepository.findById(mainCategoryId)
                .orElseThrow(() -> new CategoryHandler(ErrorStatus.MAIN_CATEGORY_NOT_FOUND));

        return subCategoryRepository.findAllByMainCategory(main).stream()
                .map(CategoryConverter::toSubCategoryDTO)
                .collect(Collectors.toList());
    }
}
