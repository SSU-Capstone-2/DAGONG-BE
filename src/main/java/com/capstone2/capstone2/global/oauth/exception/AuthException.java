package com.capstone2.capstone2.global.oauth.exception;

import com.capstone2.capstone2.global.error.code.status.ErrorStatus;
import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

    private final ErrorStatus errorStatus;

    public AuthException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }
}