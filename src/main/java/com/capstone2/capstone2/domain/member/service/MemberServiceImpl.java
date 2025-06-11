package com.capstone2.capstone2.domain.member.service;

import com.capstone2.capstone2.domain.member.converter.MemberConverter;
import com.capstone2.capstone2.domain.member.dto.MemberCategoryRequestDTO;
import com.capstone2.capstone2.domain.member.dto.MemberCategoryResponseDTO;
import com.capstone2.capstone2.domain.member.dto.MemberResponseDTO;
import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.member.handler.MemberHandler;
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

//    @Override
//    @Transactional
//    public MemberResponseDTO.InfoDTO updateCategories(Long memberId, String mainCategory1, String subCategory2) {
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
//
//        member.setMainCategory(mainCategory1);
//        member.setSubCategory(subCategory2);
//
//        return MemberConverter.toInfoDTO(member);
//    }
    private static final int MAX = 5;
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

        // 완전 교체: 기존 설정 초기화
        member.setMainCategory(null);
        member.setSubCategory(null);

        List<MemberCategoryResponseDTO> results = new ArrayList<>();

        for (MemberCategoryRequestDTO dto : reqList) {
            // 요청 DTO의 값을 그대로 엔티티에 설정
            member.setMainCategory(dto.getMainCategory());
            member.setSubCategory(dto.getSubCategory());

            // 저장 및 즉시 반영
            Member saved = memberRepository.save(member);

            // 변환기 활용
            results.add(MemberConverter.toCategoryResponseDTO(saved));
        }

        return results;
    }

}
