package com.capstone2.capstone2.domain.chat.converter;

import com.capstone2.capstone2.domain.chat.dto.ChatRoomDTO;
import com.capstone2.capstone2.domain.chat.entity.ChatMessage;
import com.capstone2.capstone2.domain.chat.entity.ChatRoom;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ChatRoomConverter {
    public ChatRoomDTO toChatRoomDTO(ChatRoom room, ChatMessage last, boolean unread) {
        return ChatRoomDTO.builder()
                .chatRoomId(room.getId())
                .groupPurchaseId(room.getGroupPurchase().getId())
                .roomName(room.getGroupPurchase().getName())
                .participants(room.getGroupPurchase().getCurrentParticipants())
                .lastMessage(last != null ? last.getContent() : null)
                .lastSentAt(last != null ? last.getCreatedAt() : null)
                .unread(unread)
                .build();
    }
}
