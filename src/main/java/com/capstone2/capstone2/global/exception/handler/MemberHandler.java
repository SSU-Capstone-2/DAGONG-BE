package com.capstone2.capstone2.global.exception.handler;

import com.capstone2.capstone2.global.error.code.status.BaseErrorCode;
import com.capstone2.capstone2.global.exception.GeneralException;

public class MemberHandler extends GeneralException {
    public MemberHandler(BaseErrorCode baseErrorCode){
        super(baseErrorCode);
    }
}
