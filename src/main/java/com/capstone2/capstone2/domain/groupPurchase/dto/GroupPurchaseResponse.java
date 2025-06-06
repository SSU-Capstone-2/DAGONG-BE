package com.capstone2.capstone2.domain.groupPurchase.dto;

import com.capstone2.capstone2.domain.model.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

public class GroupPurchaseResponse {

    @Data
    @Builder
    public static class GroupPurchaseIdDTO {
        private Long groupPurchaseId;
    }

    @Data
    @Builder
    public static class GroupPurchaseListDTO {
        private Long id;
        private String title;
        private Status status;
        private String place;
        private int price;
        private int maxParticipants;
        private int currentParticipants;
        private int views;
        private int likes;
        private LocalDateTime deadline;
    }

    @Data
    @Builder
    public static class GroupPurchaseDetailDTO {
        private Long id;
        private String title;
        private String content;
        private String place;
        private Status status;
        private String name;
        private int quantity;
        private List<String> imageUrls;
        private int maxParticipants;
        private int currentParticipants;
        private String writerName;
        private String category1;
        private String category2;
        private int views;
        private int likes;
        private LocalDateTime deadline;
        private LocalDateTime createdAt;
    }
}
