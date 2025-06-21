package com.capstone2.capstone2.domain.groupPurchase.repository;

import com.capstone2.capstone2.domain.groupPurchase.entity.GroupPurchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.capstone2.capstone2.domain.model.enums.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface GroupPurchaseRepository extends JpaRepository<GroupPurchase, Long> {
    Page<GroupPurchase> findByCategory1(String category1, Pageable pageable);
    Page<GroupPurchase> findByCategory1AndCategory2(String category1, String category2, Pageable pageable);

    // 아이템명 + 동네 필터
    Page<GroupPurchase> findByNameContainingAndWriter_CurrentTown_Id(
            String name, Long townId, Pageable pageable);

    // 아이템명 + 카테고리1 + 동네 필터
    Page<GroupPurchase> findByNameContainingAndCategory1AndWriter_CurrentTown_Id(
            String name, String category1, Long townId, Pageable pageable);

    Page<GroupPurchase> findByStatusAndCurrentDistrictId(
            Status status,
            Long currentDistrictId,
            Pageable pageable
    );

    @Query("""
  SELECT g FROM GroupPurchase g
  JOIN District d ON g.currentDistrictId = d.id
  WHERE LOWER(g.name) LIKE LOWER(CONCAT('%',:name,'%'))
    AND LOWER(d.name) = LOWER(:districtName)
""")
    Page<GroupPurchase> searchByNameAndDistrictName(
            @Param("name") String name,
            @Param("districtName") String districtName,
            Pageable pageable
    );

    // ② 품목명 + 카테고리 + 구/군명 검색
    @Query("""
    SELECT g
      FROM GroupPurchase g
      JOIN District d ON g.currentDistrictId = d.id
     WHERE LOWER(g.name) LIKE LOWER(CONCAT('%', :name, '%'))
       AND LOWER(g.category1) = LOWER(:category1)
       AND LOWER(d.name) = LOWER(:districtName)
  """)
    Page<GroupPurchase> searchByNameAndCategoryAndDistrictName(
            @Param("name") String name,
            @Param("category1") String category1,
            @Param("districtName") String districtName,
            Pageable pageable
    );


    Page<GroupPurchase> findByWriter_Id(Long writerId, Pageable pageable);
}
