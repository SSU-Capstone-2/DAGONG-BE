package com.capstone2.capstone2.domain.chat.controller;

import com.capstone2.capstone2.domain.chat.dto.ChatRoomDTO;
import com.capstone2.capstone2.domain.chat.dto.MemberInfoDTO;
import com.capstone2.capstone2.domain.chat.service.ChatRoomService;
import com.capstone2.capstone2.domain.chat.service.ChatService;
import com.capstone2.capstone2.domain.location.dto.MemberCoordinatesDTO;
import com.capstone2.capstone2.global.common.response.ApiResponse;
import com.capstone2.capstone2.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
@Tag(name = "채팅방 API", description = "채팅방 관련 API")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatService chatService;

    @GetMapping("/rooms/{memberId}")
    @Operation(summary = "채팅방 목록 조회", description = "채팅방 목록을 조회하는 API입니다.")
    @Parameter(name = "memberId", description = "공구를 등록하는 멤버의 ID, 추후 hidden으로 수정 예정입니다.")
    public ApiResponse<List<ChatRoomDTO>> getChatRooms(@PathVariable("memberId") Long memberId) {
        List<ChatRoomDTO> rooms = chatRoomService.getChatRooms(memberId);

        return ApiResponse.onSuccess(SuccessStatus.CHATROOM_LIST_OK, rooms);
    }

    @GetMapping("/rooms/{chatRoomId}/coordinates")
    @Operation(summary = "채팅 참여자 위도경도 조회",
            description = "채팅방 참여 멤버들의 위·경도 기록(경로)를 반환합니다.")
    public ApiResponse<List<MemberCoordinatesDTO>> getMemberCoordinates(
            @PathVariable Long chatRoomId) {

        List<MemberCoordinatesDTO> data =
                chatRoomService.getMemberCoordinates(chatRoomId);
        return ApiResponse.onSuccess(SuccessStatus.CHATROOM_COORDINATES_OK, data);
    }

    @GetMapping("/rooms/{chatRoomId}/members")
    @Operation(summary = "채팅방 참여 멤버 조회",
            description = "채팅방 참여 멤버들의 ID와 닉네임을 반환합니다.")
    public ApiResponse<List<MemberInfoDTO>> getChatRoomMembers(
            @PathVariable Long chatRoomId) {
        List<MemberInfoDTO> data = chatRoomService.getChatRoomMembers(chatRoomId);
        return ApiResponse.onSuccess(SuccessStatus.CHATROOM_MEMBER_LIST_OK, data);
    }
}
