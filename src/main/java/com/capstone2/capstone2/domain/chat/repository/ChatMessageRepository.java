package com.capstone2.capstone2.domain.chat.repository;

import com.capstone2.capstone2.domain.chat.entity.ChatMessage;
import com.capstone2.capstone2.domain.chat.entity.ChatRoom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Optional<ChatMessage> findTopByChatRoomOrderByCreatedAtDesc(ChatRoom chatRoom);
    @Query("""
        SELECT m
        FROM ChatMessage m
        WHERE m.chatRoom.id = :roomId
          AND (:lastId IS NULL OR m.id < :lastId)
        ORDER BY m.id DESC
        """)
    List<ChatMessage> fetchSlice(@Param("roomId") Long roomId,
                                 @Param("lastId") Long lastId,
                                 Pageable pageable);
}
