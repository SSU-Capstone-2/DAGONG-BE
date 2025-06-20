package com.capstone2.capstone2.domain.groupPurchase.repository;

import com.capstone2.capstone2.domain.groupPurchase.entity.GroupPurchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupPurchaseRepository extends JpaRepository<GroupPurchase, Long> {
    Page<GroupPurchase> findByCategory1(String category1, Pageable pageable);
    Page<GroupPurchase> findByCategory1AndCategory2(String category1, String category2, Pageable pageable);

    // 아이템명 + 동네 필터
    Page<GroupPurchase> findByNameContainingAndWriter_CurrentTown_Id(
            String name, Long townId, Pageable pageable);

    // 아이템명 + 카테고리1 + 동네 필터
    Page<GroupPurchase> findByNameContainingAndCategory1AndWriter_CurrentTown_Id(
            String name, String category1, Long townId, Pageable pageable);

}
