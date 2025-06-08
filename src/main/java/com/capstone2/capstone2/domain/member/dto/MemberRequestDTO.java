package com.capstone2.capstone2.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDTO {

    @NotBlank(message = "닉네임은 비어 있을 수 없습니다.")
    private String nickname;
}
