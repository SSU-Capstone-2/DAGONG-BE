package com.capstone2.capstone2.domain.member.service;

import com.capstone2.capstone2.domain.member.dto.*;

import java.util.List;

public interface MemberService {
    MemberResponseDTO.InfoDTO getMemberInfo(Long id);

    MemberResponseDTO.InfoDTO updateNickname(Long id, String nickname);

    void deleteMember(Long id);

    List<MemberCategoryResponseDTO> updateCategories(
            Long memberId,
            List<MemberCategoryRequestDTO> reqList
    );
    MemberItemLikeResponseDto like(Long memberId, Long groupPurchaseId);
    MemberItemLikeResponseDto unlike(Long memberId, Long groupPurchaseId);
    List<MemberLikedGroupPurchaseDto> findLikedGroupPurchases(Long memberId);

}
