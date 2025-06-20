package com.capstone2.capstone2.domain.chat.repository;

import com.capstone2.capstone2.domain.chat.entity.ChatRoom;
import com.capstone2.capstone2.domain.groupPurchase.entity.GroupPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByGroupPurchase(GroupPurchase groupPurchase);
    @Query("""
        select cr
        from ChatRoom cr
        join cr.groupPurchase gp
        join gp.participations p
        where p.member.id = :memberId
        order by cr.updatedAt desc
    """)
    List<ChatRoom> findAllByMemberId(@Param("memberId") Long memberId);
}
