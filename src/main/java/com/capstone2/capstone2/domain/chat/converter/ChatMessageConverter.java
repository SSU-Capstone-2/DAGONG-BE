package com.capstone2.capstone2.domain.chat.converter;

import com.capstone2.capstone2.domain.chat.dto.ChatMessageDTO;
import com.capstone2.capstone2.domain.chat.entity.ChatMessage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ChatMessageConverter {

    public ChatMessageDTO toChatMessageDTO(ChatMessage entity) {
        return ChatMessageDTO.builder()
                .messageType(ChatMessageDTO.MessageType.valueOf(entity.getMessageType().name()))
                .chatRoomId(entity.getChatRoom().getId())
                .senderId(entity.getSender() != null ? entity.getSender().getId() : null)
                .senderNick(entity.getSender() != null ? entity.getSender().getNickname() : "SYSTEM")
                .content(entity.getContent())
                .timestamp(entity.getCreatedAt())
                .build();
    }
}