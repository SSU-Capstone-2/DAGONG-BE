package com.capstone2.capstone2.global.oauth.dto;

import com.capstone2.capstone2.domain.member.dto.MemberResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private MemberResponseDTO.JoinResultDTO user;
}
