package com.capstone2.capstone2.domain.chat.service;

import com.capstone2.capstone2.domain.chat.converter.ChatMessageConverter;
import com.capstone2.capstone2.domain.chat.dto.ChatMessageDTO;
import com.capstone2.capstone2.domain.chat.dto.ChatMessageResponse;
import com.capstone2.capstone2.domain.chat.entity.ChatMessage;
import com.capstone2.capstone2.domain.chat.entity.ChatRoom;
import com.capstone2.capstone2.domain.chat.entity.ChatRoomMember;
import com.capstone2.capstone2.domain.chat.repository.ChatMessageRepository;
import com.capstone2.capstone2.domain.chat.repository.ChatRoomMemberRepository;
import com.capstone2.capstone2.domain.chat.repository.ChatRoomRepository;
import com.capstone2.capstone2.domain.location.dto.CoordinateDTO;
import com.capstone2.capstone2.domain.location.dto.MemberCoordinatesDTO;
import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.member.handler.MemberHandler;
import com.capstone2.capstone2.domain.member.repository.MemberRepository;
import com.capstone2.capstone2.global.error.code.status.ErrorStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.capstone2.capstone2.domain.location.repository.MemberCoordinateRepository;
//import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final MemberCoordinateRepository memberCoordinateRepository;

    @Override
    @Transactional
    public ChatMessageDTO saveTalk(ChatMessageDTO chatMessageDTO, Long memberId) {


        Member sender = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_ID_NULL));

        ChatRoom room = chatRoomRepository.getReferenceById(chatMessageDTO.getChatRoomId());
        ChatMessage entity = ChatMessage.talk(sender, room, chatMessageDTO.getContent());
        room.addMessage(entity);

        chatMessageRepository.save(entity);
        return ChatMessageConverter.toChatMessageDTO(entity);    }

    @Override
    @Transactional
    public ChatMessageDTO saveSystem(ChatMessageDTO chatMessageDTO, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_ID_NULL));

        String content = member.getNickname() + "님이 입장하셨습니다.";
        ChatRoom chatRoom = chatRoomRepository.getReferenceById(chatMessageDTO.getChatRoomId());
        ChatMessage sys = ChatMessage.system(chatRoom, content);
        chatRoom.addMessage(sys);
        chatMessageRepository.save(sys);
        return ChatMessageConverter.toChatMessageDTO(sys);
    }

}
