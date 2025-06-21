package com.capstone2.capstone2.domain.groupPurchase.repository;

import com.capstone2.capstone2.domain.groupPurchase.entity.GroupPurchase;
import com.capstone2.capstone2.domain.groupPurchase.entity.Participation;
import com.capstone2.capstone2.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    boolean existsByMemberAndGroupPurchase(Member member, GroupPurchase groupPurchase);
    Optional<Participation> findByGroupPurchaseIdAndMemberId(Long groupPurchaseId, Long memberId);
    Page<Participation> findByMember_Id(Long memberId, Pageable pageable);
    List<Participation> findAllByGroupPurchaseId(Long groupPurchaseId);

}
