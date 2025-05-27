package com.capstone2.capstone2.domain.chat.repository;

import com.capstone2.capstone2.domain.chat.entity.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
}
