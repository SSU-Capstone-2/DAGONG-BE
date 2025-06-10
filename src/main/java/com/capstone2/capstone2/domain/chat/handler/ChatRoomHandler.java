package com.capstone2.capstone2.domain.chat.handler;

import com.capstone2.capstone2.global.error.code.status.BaseErrorCode;
import com.capstone2.capstone2.global.exception.GeneralException;

public class ChatRoomHandler extends GeneralException {
    public ChatRoomHandler(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}