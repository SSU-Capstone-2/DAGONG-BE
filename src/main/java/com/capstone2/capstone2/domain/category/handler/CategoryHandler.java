package com.capstone2.capstone2.domain.category.handler;

import com.capstone2.capstone2.global.error.code.status.BaseErrorCode;
import com.capstone2.capstone2.global.exception.GeneralException;

public class CategoryHandler extends GeneralException {

    public CategoryHandler(BaseErrorCode baseErrorCode){
        super(baseErrorCode);
    }

}