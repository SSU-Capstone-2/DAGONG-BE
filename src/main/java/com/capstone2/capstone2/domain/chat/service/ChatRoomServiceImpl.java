package com.capstone2.capstone2.domain.chat.service;

import com.capstone2.capstone2.domain.chat.converter.ChatRoomConverter;
import com.capstone2.capstone2.domain.chat.dto.ChatRoomDTO;
import com.capstone2.capstone2.domain.chat.entity.ChatMessage;
import com.capstone2.capstone2.domain.chat.repository.ChatMessageRepository;
import com.capstone2.capstone2.domain.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public List<ChatRoomDTO> getChatRooms(Long memberId) {

        return chatRoomRepository.findAllByMemberId(memberId).stream()
                .map(room -> {
                    ChatMessage last =
                            chatMessageRepository
                                    .findTopByChatRoomOrderByCreatedAtDesc(room)
                                    .orElse(null);

                    // ↘️ 읽음 여부 로직이 있다면 here
                    boolean unread = false;

                    return ChatRoomConverter.toChatRoomDTO(room, last, unread);
                })
                .toList();
    }
}
