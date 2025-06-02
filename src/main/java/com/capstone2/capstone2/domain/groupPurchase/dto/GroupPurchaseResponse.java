package com.capstone2.capstone2.domain.groupPurchase.dto;

import lombok.Builder;
import lombok.Data;

public class GroupPurchaseResponse {

    @Data
    @Builder
    public static class GroupPurchaseIdDTO {
        Long groupPurchaseId;
    }
}
