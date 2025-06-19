package com.capstone2.capstone2.domain.location.handler;

import com.capstone2.capstone2.global.error.code.status.BaseErrorCode;
import com.capstone2.capstone2.global.error.code.status.ErrorStatus;
import com.capstone2.capstone2.global.exception.GeneralException;


public class LocationHandler extends GeneralException {
    public LocationHandler(BaseErrorCode baseErrorCode){
        super(baseErrorCode);
    }

}