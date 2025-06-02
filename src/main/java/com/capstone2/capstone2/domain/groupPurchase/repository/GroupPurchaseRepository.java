package com.capstone2.capstone2.domain.groupPurchase.repository;

import com.capstone2.capstone2.domain.groupPurchase.entity.GroupPurchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupPurchaseRepository extends JpaRepository<GroupPurchase, Long> {
    Page<GroupPurchase> findByCategory1(String category1, Pageable pageable);
    Page<GroupPurchase> findByCategory1AndCategory2(String category1, String category2, Pageable pageable);
}
