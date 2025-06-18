package com.capstone2.capstone2.domain.chat.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChatMessageDTO {

    public enum MessageType {
        TALK, SYSTEM, NOTICE
    }

    private MessageType messageType;
    private Long chatRoomId; // 구독 경로 구분
    private Long senderId;
    private String senderNick;
    private String content;
    private LocalDateTime timestamp;
}
