package com.capstone2.capstone2.domain.member.service;

import com.capstone2.capstone2.domain.member.dto.MemberCategoryRequestDTO;
import com.capstone2.capstone2.domain.member.dto.MemberCategoryResponseDTO;
import com.capstone2.capstone2.domain.member.dto.MemberResponseDTO;

import java.util.List;

public interface MemberService {
    MemberResponseDTO.InfoDTO getMemberInfo(Long id);

    MemberResponseDTO.InfoDTO updateNickname(Long id, String nickname);

    void deleteMember(Long id);

    List<MemberCategoryResponseDTO> updateCategories(
            Long memberId,
            List<MemberCategoryRequestDTO> reqList
    );
}
