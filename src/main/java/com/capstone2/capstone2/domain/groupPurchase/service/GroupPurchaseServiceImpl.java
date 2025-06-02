package com.capstone2.capstone2.domain.groupPurchase.service;

import com.capstone2.capstone2.domain.groupPurchase.converter.GroupPurchaseConverter;
import com.capstone2.capstone2.domain.groupPurchase.dto.GroupPurchaseRequest;
import com.capstone2.capstone2.domain.groupPurchase.dto.GroupPurchaseResponse;
import com.capstone2.capstone2.domain.groupPurchase.entity.GroupPurchase;
import com.capstone2.capstone2.domain.groupPurchase.entity.GroupPurchaseImage;
import com.capstone2.capstone2.domain.groupPurchase.handler.GroupPurchaseHandler;
import com.capstone2.capstone2.domain.groupPurchase.repository.GroupPurchaseImageRepository;
import com.capstone2.capstone2.domain.groupPurchase.repository.GroupPurchaseRepository;
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

    // 공구 생성
    @Transactional
    @Override
    public GroupPurchase createGroupPurchase(Long memberId, GroupPurchaseRequest.GroupPurchaseCreateDTO request) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_ID_NULL));

        GroupPurchase groupPurchase = groupPurchaseRepository.save(
                GroupPurchaseConverter.toGroupPurchase(member, request)
        );

        GroupPurchaseImage image = GroupPurchaseConverter.toGroupPurchaseImage(groupPurchase, request.getImage());
        groupPurchaseImageRepository.save(image);

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
}
