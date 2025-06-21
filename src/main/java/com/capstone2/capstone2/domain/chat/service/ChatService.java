package com.capstone2.capstone2.domain.chat.service;

import com.capstone2.capstone2.domain.chat.dto.ChatMessageDTO;
import com.capstone2.capstone2.domain.chat.dto.ChatMessageResponse;
import com.capstone2.capstone2.domain.location.dto.MemberCoordinatesDTO;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ChatService {
    ChatMessageDTO saveTalk(ChatMessageDTO chatMessageDTO, Long memberId);
    ChatMessageDTO saveSystem(ChatMessageDTO chatMessageDTO, Long memberId);
}
