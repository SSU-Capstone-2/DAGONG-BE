package com.capstone2.capstone2.domain.member.service;

import com.capstone2.capstone2.domain.groupPurchase.entity.GroupPurchase;
import com.capstone2.capstone2.domain.groupPurchase.handler.GroupPurchaseHandler;
import com.capstone2.capstone2.domain.groupPurchase.repository.GroupPurchaseRepository;
import com.capstone2.capstone2.domain.member.converter.MemberConverter;
import com.capstone2.capstone2.domain.member.dto.MemberCategoryRequestDTO;
import com.capstone2.capstone2.domain.member.dto.MemberCategoryResponseDTO;
import com.capstone2.capstone2.domain.member.dto.MemberItemLikeResponseDto;
import com.capstone2.capstone2.domain.member.dto.MemberResponseDTO;
import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.member.entity.MemberFavoriteCategory;
import com.capstone2.capstone2.domain.member.entity.MemberItemLike;
import com.capstone2.capstone2.domain.member.handler.MemberHandler;
import com.capstone2.capstone2.domain.member.repository.MemberFavoriteCategoryRepository;
import com.capstone2.capstone2.domain.member.repository.MemberItemLikeRepository;
import com.capstone2.capstone2.domain.member.repository.MemberRepository;
import com.capstone2.capstone2.global.error.code.status.ErrorStatus;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

            results.add(MemberConverter.toCategoryResponseDTO(saved));
        }

        return results;
    }

    private final GroupPurchaseRepository groupPurchaseRepository;
    private final MemberItemLikeRepository likeRepository;

    @Override
    @Transactional
    public MemberItemLikeResponseDto like(Long memberId, Long groupPurchaseId) {
        if (likeRepository.existsByMemberIdAndGroupPurchaseId(memberId, groupPurchaseId)) {
            throw new MemberHandler(ErrorStatus.ALREADY_LIKED);

        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        GroupPurchase gp = groupPurchaseRepository.findById(groupPurchaseId)
                .orElseThrow(() -> new GroupPurchaseHandler(ErrorStatus.GROUP_PURCHASE_NOT_FOUND));

        MemberItemLike like = MemberItemLike.builder()
                .member(member)
                .groupPurchase(gp)
                .build();
        likeRepository.save(like);

        gp.increaseLikes();
        return new MemberItemLikeResponseDto(member.getId(), gp.getId());

    }

    @Override
    @Transactional
    public MemberItemLikeResponseDto unlike(Long memberId, Long groupPurchaseId) {
        MemberItemLike like = likeRepository
                .findByMemberIdAndGroupPurchaseId(memberId, groupPurchaseId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.LIKE_NOT_FOUND));

        likeRepository.delete(like);

        GroupPurchase gp = like.getGroupPurchase();
        gp.decreaseLikes();

        return new MemberItemLikeResponseDto(memberId, groupPurchaseId);
    }


    @Override
    @Transactional(readOnly = true)
    public List<MemberItemLikeResponseDto> findLikesByMember(Long memberId) {
        // (1) 멤버 존재 체크
        if (!memberRepository.existsById(memberId)) {
            throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }

        // (2) 매핑 테이블에서 해당 멤버의 Like 목록 조회
        List<MemberItemLike> likes = likeRepository.findAllByMemberId(memberId);

        // (3) DTO로 변환
        return likes.stream()
                .map(like -> new MemberItemLikeResponseDto(
                        like.getMember().getId(),
                        like.getGroupPurchase().getId()))
                .collect(Collectors.toList());
    }

}
