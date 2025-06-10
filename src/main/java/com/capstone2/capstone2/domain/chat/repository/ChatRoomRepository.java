package com.capstone2.capstone2.domain.chat.repository;

import com.capstone2.capstone2.domain.chat.entity.ChatRoom;
import com.capstone2.capstone2.domain.groupPurchase.entity.GroupPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByGroupPurchase(GroupPurchase groupPurchase);
}
