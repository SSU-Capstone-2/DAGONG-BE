package com.capstone2.capstone2.domain.member.service;

import com.capstone2.capstone2.domain.member.dto.MemberResponseDTO;

public interface MemberService {
    MemberResponseDTO.InfoDTO getMemberInfo(Long id);
}
