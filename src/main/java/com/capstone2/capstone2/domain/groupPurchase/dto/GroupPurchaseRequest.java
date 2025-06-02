package com.capstone2.capstone2.domain.groupPurchase.dto;

import lombok.Builder;
import lombok.Data;

public class GroupPurchaseRequest {

    @Data
    @Builder
    public static class GroupPurchaseCreateDTO {
        String title;
        String name;
        String image;
        String category1;
        String category2;
        String content;
        int price;
        int quantity;
        int participants;
    }
}
