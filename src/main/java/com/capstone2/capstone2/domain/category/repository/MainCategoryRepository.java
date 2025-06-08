package com.capstone2.capstone2.domain.category.repository;

import com.capstone2.capstone2.domain.category.entity.MainCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainCategoryRepository extends JpaRepository<MainCategory, Long> {
}
