package com.capstone2.capstone2.domain.groupPurchase.service;

import com.capstone2.capstone2.domain.groupPurchase.converter.GroupPurchaseConverter;
import com.capstone2.capstone2.domain.groupPurchase.dto.GroupPurchaseRequest;
import com.capstone2.capstone2.domain.groupPurchase.dto.GroupPurchaseResponse;
import com.capstone2.capstone2.domain.groupPurchase.entity.GroupPurchase;
import com.capstone2.capstone2.domain.groupPurchase.entity.GroupPurchaseImage;
import com.capstone2.capstone2.domain.groupPurchase.entity.Participation;
import com.capstone2.capstone2.domain.groupPurchase.handler.GroupPurchaseHandler;
import com.capstone2.capstone2.domain.groupPurchase.repository.GroupPurchaseImageRepository;
import com.capstone2.capstone2.domain.groupPurchase.repository.GroupPurchaseRepository;
import com.capstone2.capstone2.domain.groupPurchase.repository.ParticipationRepository;
import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.member.handler.MemberHandler;
import com.capstone2.capstone2.domain.member.repository.MemberRepository;
import com.capstone2.capstone2.global.error.code.status.ErrorStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GroupPurchaseServiceImpl implements GroupPurchaseService{

    private final MemberRepository memberRepository;
    private final GroupPurchaseRepository groupPurchaseRepository;
    private final GroupPurchaseImageRepository groupPurchaseImageRepository;
    private final ParticipationRepository participationRepository;

    // 공구 생성
    @Transactional
    @Override
    public GroupPurchase createGroupPurchase(Long memberId, GroupPurchaseRequest.GroupPurchaseCreateDTO request) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_ID_NULL));

        // 1. 공구 생성
        GroupPurchase groupPurchase = groupPurchaseRepository.save(
                GroupPurchaseConverter.toCreateGroupPurchase(member, request)
        );

        // 2. 이미지 저장
        GroupPurchaseImage image = GroupPurchaseConverter.toGroupPurchaseImage(groupPurchase, request.getImageUrl());
        groupPurchaseImageRepository.save(image);

        // 3. 작성자 본인을 참여자로 추가
        Participation participation = Participation.builder()
                .member(member)
                .groupPurchase(groupPurchase)
                .build();
        participationRepository.save(participation);
        groupPurchase.addParticipation(participation); // 리스트 추가 + 참여자 수 증가

        return groupPurchase;
    }

    // 공구 전체 조회
    @Override
    public Page<GroupPurchaseResponse.GroupPurchaseListDTO> getAllPurchases(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<GroupPurchase> groupPurchases = groupPurchaseRepository.findAll(pageable);

        return groupPurchases.map(GroupPurchaseConverter::toGroupPurchaseListDTO);
    }

    // 공구 상세 조회
    @Override
    @Transactional
    public GroupPurchaseResponse.GroupPurchaseDetailDTO getGroupPurchaseDetail(Long purchaseId) {
        GroupPurchase groupPurchase = groupPurchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new GroupPurchaseHandler(ErrorStatus.GROUP_PURCHASE_ID_NULL));

        groupPurchase.increaseViews(); // 조회 시 조회수 1 증가

        String writerName = groupPurchase.getWriter().getNickname();
        List<String> imageUrls = groupPurchase.getGroupPurchaseImages().stream()
                .map(GroupPurchaseImage::getImageUrl)
                .collect(Collectors.toList());

        GroupPurchaseResponse.GroupPurchaseDetailDTO response = GroupPurchaseConverter.toGroupPurchaseDetailDTO(groupPurchase, writerName, imageUrls);
        return response;
    }

    // 공구 정보 수정
    @Override
    @Transactional
    public GroupPurchase updateGroupPurchase(Long groupPurchaseId, GroupPurchaseRequest.GroupPurchaseUpdateDTO request) {
        GroupPurchase groupPurchase = groupPurchaseRepository.findById(groupPurchaseId)
                .orElseThrow(() -> new GroupPurchaseHandler(ErrorStatus.GROUP_PURCHASE_ID_NULL));

        groupPurchase.updateGroupPurchase(request);
        return groupPurchase;
    }

    // 공구 삭제
    @Override
    @Transactional
    public void deleteGroupPurchase(Long groupPurchaseId) {
        GroupPurchase groupPurchase = groupPurchaseRepository.findById(groupPurchaseId)
                .orElseThrow(() -> new GroupPurchaseHandler(ErrorStatus.GROUP_PURCHASE_ID_NULL));

        groupPurchaseRepository.delete(groupPurchase);
    }

    // 인기 공구 목록 조회
    @Override
    public Page<GroupPurchaseResponse.GroupPurchaseListDTO> getPopularGroupPurchases(String sort, int page, int size) {
        Sort sorting;
        if (sort.equalsIgnoreCase("likes")) {
            sorting = Sort.by(Sort.Order.desc("likes"), Sort.Order.desc("views"));
        } else {
            sorting = Sort.by(Sort.Order.desc("views"), Sort.Order.desc("likes"));
        }

        Pageable pageable = PageRequest.of(page -1 , size, sorting);
        Page<GroupPurchase> groupPurchases = groupPurchaseRepository.findAll(pageable);

        return groupPurchases.map(GroupPurchaseConverter::toGroupPurchaseListDTO);
    }


    // 카테고리 별 공구 목록 조회
    @Override
    public Page<GroupPurchaseResponse.GroupPurchaseListDTO> getGroupPurchasesByCategory(String category1, String category2, int page, int size) {
        Pageable pageable = PageRequest.of(page -1 , size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<GroupPurchase> groupPurchases;

        if (category2 != null && !category2.isBlank()) {
            groupPurchases = groupPurchaseRepository.findByCategory1AndCategory2(category1, category2, pageable);
        } else {
            groupPurchases = groupPurchaseRepository.findByCategory1(category1, pageable);
        }

        return groupPurchases.map(GroupPurchaseConverter::toGroupPurchaseListDTO);
    }

    // 공구 참여
    @Override
    @Transactional
    public GroupPurchase participateGroupPurchase(Long groupPurchaseId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_ID_NULL));

        GroupPurchase groupPurchase = groupPurchaseRepository.findById(groupPurchaseId)
                .orElseThrow(() -> new GroupPurchaseHandler(ErrorStatus.GROUP_PURCHASE_ID_NULL));

        // 중복 참여 방지
        boolean alreadyParticipated = participationRepository.existsByMemberAndGroupPurchase(member, groupPurchase);
        if (alreadyParticipated) {
            throw new GroupPurchaseHandler(ErrorStatus.PARTICIPATION_ALREADY);
        }

        Participation participation = Participation.builder()
                .member(member)
                .groupPurchase(groupPurchase)
                .build();

        participationRepository.save(participation);
        groupPurchase.addParticipation(participation);

        return groupPurchase;
    }
}
