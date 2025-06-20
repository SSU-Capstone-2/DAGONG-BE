package com.capstone2.capstone2.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ChatRoomDTO {
    private Long chatRoomId;
    private Long groupPurchaseId;
    private String roomName;
    private int participants; // 현재 참여자
    private String lastMessage; // 최근 메시지 본문
    private LocalDateTime lastSentAt; // 최근 메시지 발송 시각
    private boolean unread; // 안 읽은 메시지 유무
}
