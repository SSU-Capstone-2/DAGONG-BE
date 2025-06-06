package com.capstone2.capstone2.domain.member.converter;

import com.capstone2.capstone2.domain.member.dto.MemberResponseDTO;
import com.capstone2.capstone2.domain.member.entity.Member;

public class MemberConverter {
    public static MemberResponseDTO.JoinResultDTO toJoinResultDTO(Member member) {
        return MemberResponseDTO.JoinResultDTO.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }

    // 테스트용: token을 함께 반환
    public static MemberResponseDTO.JoinResultDTO toJoinResultDTO_WithToken(Member m, String jwtToken) {
        return MemberResponseDTO.JoinResultDTO.builder()
                .id(m.getId())
                .nickname(m.getNickname())
                .email(m.getEmail())
                .token(jwtToken)
                .build();
    }

}
