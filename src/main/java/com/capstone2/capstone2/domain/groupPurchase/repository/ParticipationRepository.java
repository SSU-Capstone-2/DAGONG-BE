package com.capstone2.capstone2.domain.groupPurchase.repository;

import com.capstone2.capstone2.domain.groupPurchase.entity.GroupPurchase;
import com.capstone2.capstone2.domain.groupPurchase.entity.Participation;
import com.capstone2.capstone2.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    boolean existsByMemberAndGroupPurchase(Member member, GroupPurchase groupPurchase);
}
