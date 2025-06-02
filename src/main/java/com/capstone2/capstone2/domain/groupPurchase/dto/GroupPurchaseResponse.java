package com.capstone2.capstone2.domain.groupPurchase.dto;

import com.capstone2.capstone2.domain.model.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class GroupPurchaseResponse {

    @Data
    @Builder
    public static class GroupPurchaseIdDTO {
        Long groupPurchaseId;
    }

    @Data
    @Builder
    public static class GroupPurchaseListDTO {
        private Long id;
        private String title;
        private Status status;
        private String place;
        private int price;
        private int participants;
        private int views;
        private int likes;
        private LocalDateTime deadline;
    }
}
