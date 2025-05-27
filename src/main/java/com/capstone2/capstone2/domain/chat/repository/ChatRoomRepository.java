package com.capstone2.capstone2.domain.chat.repository;

import com.capstone2.capstone2.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
