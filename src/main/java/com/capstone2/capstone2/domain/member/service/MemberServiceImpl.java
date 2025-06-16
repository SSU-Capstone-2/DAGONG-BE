package com.capstone2.capstone2.domain.member.service;

import com.capstone2.capstone2.domain.member.converter.MemberConverter;
import com.capstone2.capstone2.domain.member.dto.MemberCategoryRequestDTO;
import com.capstone2.capstone2.domain.member.dto.MemberCategoryResponseDTO;
import com.capstone2.capstone2.domain.member.dto.MemberResponseDTO;
import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.member.entity.MemberFavoriteCategory;
import com.capstone2.capstone2.domain.member.handler.MemberHandler;
import com.capstone2.capstone2.domain.member.repository.MemberFavoriteCategoryRepository;
import com.capstone2.capstone2.domain.member.repository.MemberRepository;
import com.capstone2.capstone2.global.error.code.status.ErrorStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberResponseDTO.InfoDTO getMemberInfo(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_ID_NULL));
        return MemberConverter.toInfoDTO(member);
    }
    @Override
    public MemberResponseDTO.InfoDTO updateNickname(Long id, String nickname) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        member.setNickname(nickname);
        Member saved = memberRepository.save(member);

        return MemberConverter.toInfoDTO(saved);
    }

    @Override
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        memberRepository.delete(member);
    }


    private static final int MAX = 5;

    private final MemberFavoriteCategoryRepository favRepo;

    @Override
    @Transactional
    public List<MemberCategoryResponseDTO> updateCategories(
            Long memberId,
            List<MemberCategoryRequestDTO> reqList) {

        if (reqList.size() > MAX) {
            throw new MemberHandler(ErrorStatus.CATEGORY_UPDATE_EXCEED_LIMIT);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        favRepo.deleteAllByMember(member);

        List<MemberCategoryResponseDTO> results = new ArrayList<>();
        for (MemberCategoryRequestDTO dto : reqList) {
            String sub = dto.getSubCategory();
            if (sub == null || sub.isBlank()) {
                sub = null;
            }
            MemberFavoriteCategory fav = MemberFavoriteCategory.builder()
                    .member(member)
                    .mainCategory(dto.getMainCategory())
                    .subCategory(dto.getSubCategory())
                    .build();
            MemberFavoriteCategory saved = favRepo.save(fav);

            // 3) 엔티티 → DTO 변환
            results.add(MemberConverter.toCategoryResponseDTO(saved));
        }

        return results;
    }

}
