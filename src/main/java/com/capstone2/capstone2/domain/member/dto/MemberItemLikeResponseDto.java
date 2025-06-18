package com.capstone2.capstone2.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class MemberItemLikeResponseDto {
    private final Long memberId;
    private final Long groupPurchaseId;
}