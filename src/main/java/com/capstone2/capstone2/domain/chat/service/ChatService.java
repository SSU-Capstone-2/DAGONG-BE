package com.capstone2.capstone2.domain.chat.service;

import com.capstone2.capstone2.domain.chat.dto.ChatMessageDTO;
import com.capstone2.capstone2.domain.member.entity.Member;

public interface ChatService {
    public ChatMessageDTO saveTalk(ChatMessageDTO chatMessageDTO, Long memberId);
    public ChatMessageDTO saveSystem(ChatMessageDTO chatMessageDTO, Long memberId);
}


