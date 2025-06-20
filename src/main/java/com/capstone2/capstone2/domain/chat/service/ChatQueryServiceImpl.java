package com.capstone2.capstone2.domain.chat.service;

import com.capstone2.capstone2.domain.chat.converter.ChatMessageConverter;
import com.capstone2.capstone2.domain.chat.dto.ChatMessageResponse;
import com.capstone2.capstone2.domain.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ChatQueryServiceImpl implements ChatQueryService{

    private final ChatMessageRepository chatMessageRepository;
    @Override
    public Slice<ChatMessageResponse> getHistory(Long roomId, Long lastId, int size) {
        PageRequest pr = PageRequest.of(0, size, Sort.Direction.DESC, "id");
        List<ChatMessageResponse> list = chatMessageRepository.fetchSlice(roomId, lastId, pr)
                .stream()
                .map(ChatMessageConverter::toChatMessageDTO) // 기존 converter 재사용
                .map(dto -> ChatMessageResponse.builder()
                        .id(dto.getChatRoomId())
                        .type(dto.getMessageType().name())
                        .senderId(dto.getSenderId())
                        .senderNick(dto.getSenderNick())
                        .content(dto.getContent())
                        .timestamp(dto.getTimestamp())
                        .build())
                .toList();
        boolean hasNext = list.size() == size;
        return new SliceImpl<>(list, pr, hasNext);
    }
}
