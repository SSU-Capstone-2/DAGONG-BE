package com.capstone2.capstone2.domain.chat.controller;

import com.capstone2.capstone2.domain.chat.dto.ChatMessageResponse;
import com.capstone2.capstone2.domain.chat.service.ChatQueryService;
import com.capstone2.capstone2.global.common.response.ApiResponse;
import com.capstone2.capstone2.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat/rooms")
@Tag(name = "채팅 조회 API", description = "채팅방 히스토리(무한 스크롤) 조회")
public class ChatQueryController {

    private final ChatQueryService queryService;

    @Operation(
            summary     = "채팅방 메시지 조회",
            description = """
            - 첫 진입: 'lastId' 없이 호출하면 최신 메시지부터 'size'개 반환  
            - 더 불러오기: 현재 목록의 가장 오래된 'messageId'를 'lastId'로 넘기면  
              그보다 작은 번호의 메시지를 다시 'size'개 내려줍니다.  
            - 응답 'hasNext = false'이면 더 이상 불러올 데이터가 없습니다.
            """
    )
    @Parameters({
            @Parameter(name = "roomId", description = "채팅방 ID", required = true, example = "1"),
            @Parameter(name = "lastId", description = "(커서) 이전 페이지 마지막 메시지 ID", example = "3210"),
            @Parameter(name = "size", description = "가져올 개수(1-100)", example = "30")
    })
    @GetMapping("/{roomId}/messages")
    public ApiResponse<?> getMessages(@PathVariable Long roomId,
                                      @RequestParam(required = false) Long lastId,
                                      @RequestParam(defaultValue = "20") int size) {

        Slice<ChatMessageResponse> slice = queryService.getHistory(roomId, lastId, size);
        return ApiResponse.onSuccess(SuccessStatus.CHATROOM_GET_OK, Map.of(
                "messages", slice.getContent(),
                "hasNext", slice.hasNext()
        ));
    }
}