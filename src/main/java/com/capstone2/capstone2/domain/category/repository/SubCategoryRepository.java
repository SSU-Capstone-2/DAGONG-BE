package com.capstone2.capstone2.domain.category.repository;

import com.capstone2.capstone2.domain.category.entity.MainCategory;
import com.capstone2.capstone2.domain.category.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    List<SubCategory> findAllByMainCategory(MainCategory mainCategory);
}
