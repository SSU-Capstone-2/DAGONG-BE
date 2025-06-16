package com.capstone2.capstone2.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberCategoryResponseDTO {
    private Long id;
    private String mainCategory;
    private String subCategory;

}
