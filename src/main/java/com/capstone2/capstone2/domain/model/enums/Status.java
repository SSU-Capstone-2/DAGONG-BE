package com.capstone2.capstone2.domain.model.enums;

import lombok.Getter;

@Getter
public enum Status {
    ACTIVE("등록"),
    PROCEEDING("진행중"),
    COMPLETE("완료");

    private final String status;

    Status(String status) { this.status = status; }
}
