package com.capstone2.capstone2.domain.member.repository;

import com.capstone2.capstone2.domain.member.entity.MemberItemLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberItemLikeRepository extends JpaRepository<MemberItemLike, Long> {
    boolean existsByMemberIdAndGroupPurchaseId(Long memberId, Long groupPurchaseId);
    Optional<MemberItemLike> findByMemberIdAndGroupPurchaseId(Long memberId, Long groupPurchaseId);
    List<MemberItemLike> findAllByMemberId(Long memberId);

}
