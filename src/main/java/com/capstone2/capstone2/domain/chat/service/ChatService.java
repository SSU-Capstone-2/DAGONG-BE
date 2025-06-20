package com.capstone2.capstone2.domain.chat.service;

import com.capstone2.capstone2.domain.chat.dto.ChatMessageDTO;

public interface ChatService {
    ChatMessageDTO saveTalk(ChatMessageDTO chatMessageDTO, Long memberId);
    ChatMessageDTO saveSystem(ChatMessageDTO chatMessageDTO, Long memberId);
}
