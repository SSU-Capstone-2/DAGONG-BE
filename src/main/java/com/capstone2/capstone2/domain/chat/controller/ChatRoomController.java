package com.capstone2.capstone2.domain.chat.controller;

import com.capstone2.capstone2.domain.chat.dto.ChatRoomDTO;
import com.capstone2.capstone2.domain.chat.service.ChatRoomService;
import com.capstone2.capstone2.global.common.response.ApiResponse;
import com.capstone2.capstone2.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/rooms/{memberId}")
    @Operation(summary = "채팅방 목록 조회", description = "채팅방 목록을 조회하는 API입니다.")
    @Parameter(name = "memberId", description = "공구를 등록하는 멤버의 ID, 추후 hidden으로 수정 예정입니다.")
    public ApiResponse<List<ChatRoomDTO>> getChatRooms(@PathVariable("memberId") Long memberId) {
        List<ChatRoomDTO> rooms = chatRoomService.getChatRooms(memberId);

        return ApiResponse.onSuccess(SuccessStatus.CHATROOM_LIST_OK, rooms);
    }
}
