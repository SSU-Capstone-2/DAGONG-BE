package com.capstone2.capstone2.domain.groupPurchase.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

public class GroupPurchaseRequest {

    @Data
    @Builder
    public static class GroupPurchaseCreateDTO {
        private String title;
        private String content;
        private String name;
        private String imageUrl;
        private String category1;
        private String category2;
        private int price;
        private int quantity;
        private int maxParticipants;
    }

    @Data
    @Builder
    public static class GroupPurchaseUpdateDTO {
        private String title;
        private String content;
        private String place;
        private String name;
        private int quantity;
        private List<String> imageUrls;
        private int maxParticipants;
        private String category1;
        private String category2;
        private LocalDateTime deadline;
    }
}
