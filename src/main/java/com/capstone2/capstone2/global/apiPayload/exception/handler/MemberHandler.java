package com.capstone2.capstone2.global.apiPayload.exception.handler;

import com.capstone2.capstone2.global.apiPayload.code.BaseErrorCode;
import com.capstone2.capstone2.global.apiPayload.exception.GeneralException;

public class MemberHandler extends GeneralException {
    public MemberHandler(BaseErrorCode baseErrorCode){
        super(baseErrorCode);
    }
}
