package com.capstone2.capstone2.global.exception;

import com.capstone2.capstone2.global.error.code.status.BaseErrorCode;
import com.capstone2.capstone2.global.error.code.status.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {
    private BaseErrorCode code;
    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }
    public ErrorReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}