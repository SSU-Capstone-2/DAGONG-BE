package com.capstone2.capstone2.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatMessageResponse {
    private Long id;
    private String type;
    private Long senderId;
    private String senderNick;
    private String content;
    private LocalDateTime timestamp;
}
