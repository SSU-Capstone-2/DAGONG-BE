package com.capstone2.capstone2.domain.chat.service;

import com.capstone2.capstone2.domain.chat.converter.ChatRoomConverter;
import com.capstone2.capstone2.domain.chat.dto.ChatRoomDTO;
import com.capstone2.capstone2.domain.chat.dto.MemberInfoDTO;
import com.capstone2.capstone2.domain.chat.entity.ChatMessage;
import com.capstone2.capstone2.domain.chat.entity.ChatRoom;
import com.capstone2.capstone2.domain.chat.repository.ChatMessageRepository;
import com.capstone2.capstone2.domain.chat.repository.ChatRoomRepository;
import com.capstone2.capstone2.domain.groupPurchase.entity.Participation;
import com.capstone2.capstone2.domain.groupPurchase.repository.ParticipationRepository;
import com.capstone2.capstone2.domain.location.dto.CoordinateDTO;
import com.capstone2.capstone2.domain.location.dto.MemberCoordinatesDTO;
import com.capstone2.capstone2.domain.location.repository.MemberCoordinateRepository;
import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ParticipationRepository participationRepository;
    private final MemberCoordinateRepository memberCoordinateRepository;
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


    @Override
    public List<MemberCoordinatesDTO> getMemberCoordinates(Long chatRoomId) {
        // 1) ChatRoom → GroupPurchase ID
        ChatRoom room = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 채팅방입니다. id=" + chatRoomId));

        Long gpId = room.getGroupPurchase().getId();

        // 2) Participation → 참여 멤버 리스트
        List<Member> members = participationRepository
                .findAllByGroupPurchaseId(gpId).stream()
                .map(Participation::getMember)
                .distinct()
                .toList();

        // 3) 멤버별 좌표 기록 조회 & DTO 변환
        return members.stream()
                .map(member -> {
                    List<CoordinateDTO> coords = memberCoordinateRepository
                            .findAllByMemberOrderByCreatedAt(member)
                            .stream()
                            .map(mc -> new CoordinateDTO(
                                    mc.getLatitude(),
                                    mc.getLongitude()
                            ))
                            .collect(Collectors.toList());

                    return new MemberCoordinatesDTO(member.getId(), coords);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MemberInfoDTO> getChatRoomMembers(Long chatRoomId) {
        ChatRoom room = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 채팅방입니다. id=" + chatRoomId));

        Long gpId = room.getGroupPurchase().getId();
        return participationRepository.findAllByGroupPurchaseId(gpId).stream()
                .map(Participation::getMember)
                .distinct()
                .map(m -> new MemberInfoDTO(
                        m.getId(),
                        m.getNickname(),
                        m.getProfile_url()      // ← 여기서 프로필 URL 꺼내서 넣어줍니다
                ))
                .collect(Collectors.toList());
    }



}
