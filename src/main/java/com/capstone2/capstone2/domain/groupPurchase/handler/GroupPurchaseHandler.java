package com.capstone2.capstone2.domain.groupPurchase.handler;

import com.capstone2.capstone2.global.error.code.status.BaseErrorCode;
import com.capstone2.capstone2.global.exception.GeneralException;

public class GroupPurchaseHandler extends GeneralException {
    public GroupPurchaseHandler(BaseErrorCode baseErrorCode){
        super(baseErrorCode);
    }

}
