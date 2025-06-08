package com.capstone2.capstone2.domain.member.handler;

import com.capstone2.capstone2.global.error.code.status.BaseErrorCode;
import com.capstone2.capstone2.global.error.code.status.ErrorStatus;
import com.capstone2.capstone2.global.exception.GeneralException;

public class MemberHandler extends GeneralException {
    public MemberHandler(BaseErrorCode baseErrorCode){
        super(baseErrorCode);
    }
}
