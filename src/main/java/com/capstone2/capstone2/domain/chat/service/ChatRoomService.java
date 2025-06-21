package com.capstone2.capstone2.domain.chat.service;

import com.capstone2.capstone2.domain.chat.dto.ChatRoomDTO;
import com.capstone2.capstone2.domain.location.dto.MemberCoordinatesDTO;

import java.util.List;

public interface ChatRoomService {
    List<ChatRoomDTO> getChatRooms(Long memberId);
    List<MemberCoordinatesDTO> getMemberCoordinates(Long chatRoomId);

}
