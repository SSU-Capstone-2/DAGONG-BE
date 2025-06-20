package com.capstone2.capstone2.domain.chat.service;

import com.capstone2.capstone2.domain.chat.dto.ChatRoomDTO;

import java.util.List;

public interface ChatRoomService {
    List<ChatRoomDTO> getChatRooms(Long memberId);
}
