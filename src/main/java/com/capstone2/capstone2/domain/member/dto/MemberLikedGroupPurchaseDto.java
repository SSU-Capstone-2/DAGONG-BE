package com.capstone2.capstone2.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLikedGroupPurchaseDto {
    private final Long groupPurchaseId;
    private final String title;
    private final String imageUrl;
}
