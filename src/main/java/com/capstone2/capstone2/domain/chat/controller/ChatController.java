package com.capstone2.capstone2.domain.chat.controller;

import com.capstone2.capstone2.domain.chat.dto.ChatMessageDTO;
import com.capstone2.capstone2.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService; // 메시지 저장 + 비즈니스 로직
    private final SimpMessagingTemplate template;

    // TALK 메시지
    @MessageMapping("/chat/send")
    public void sendMessage(@Header("memberId") Long memberId,
                            @Payload ChatMessageDTO chatMessageDTO) {
        ChatMessageDTO saved = chatService.saveTalk(chatMessageDTO, memberId);

        template.convertAndSend("/topic/room/" + saved.getChatRoomId(), saved);
    }

    // 입장 시 SYSTEM 메시지
    @MessageMapping("/chat/enter")
    public void enter(@Header("memberId") Long memberId,
                      @Payload ChatMessageDTO chatMessageDTO) {
        ChatMessageDTO system = chatService.saveSystem(chatMessageDTO, memberId);
        template.convertAndSend("/topic/room/" + system.getChatRoomId(), system);
    }

}