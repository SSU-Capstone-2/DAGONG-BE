package com.capstone2.capstone2.domain.member.service;

import com.capstone2.capstone2.domain.member.dto.MemberRequestDTO;
import com.capstone2.capstone2.domain.member.dto.MemberResponseDTO;

public interface MemberService {
    MemberResponseDTO.InfoDTO getMemberInfo(Long id);

    MemberResponseDTO.InfoDTO updateNickname(Long id, String nickname);

    void deleteMember(Long id);

    MemberResponseDTO.InfoDTO updateCategories(Long memberId, String mainCategory, String subCategory);

}
