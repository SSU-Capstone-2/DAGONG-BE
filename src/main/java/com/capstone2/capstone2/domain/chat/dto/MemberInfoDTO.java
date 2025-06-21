package com.capstone2.capstone2.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoDTO {
    private Long id;
    private String nickname;
    private String profile_url;
}
