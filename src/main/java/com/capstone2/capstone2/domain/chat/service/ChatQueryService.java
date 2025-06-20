package com.capstone2.capstone2.domain.chat.service;

import com.capstone2.capstone2.domain.chat.dto.ChatMessageResponse;
import org.springframework.data.domain.Slice;

public interface ChatQueryService {
    Slice<ChatMessageResponse> getHistory(Long roomId, Long lastId, int size);
}
