package com.capstone2.capstone2.domain.groupPurchase.service;

import com.capstone2.capstone2.domain.groupPurchase.converter.GroupPurchaseConverter;
import com.capstone2.capstone2.domain.groupPurchase.dto.GroupPurchaseRequest;
import com.capstone2.capstone2.domain.groupPurchase.entity.GroupPurchase;
import com.capstone2.capstone2.domain.groupPurchase.entity.GroupPurchaseImage;
import com.capstone2.capstone2.domain.groupPurchase.repository.GroupPurchaseImageRepository;
import com.capstone2.capstone2.domain.groupPurchase.repository.GroupPurchaseRepository;
import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.member.handler.MemberHandler;
import com.capstone2.capstone2.domain.member.repository.MemberRepository;
import com.capstone2.capstone2.global.error.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupPurchaseServiceImpl implements GroupPurchaseService{

    private final MemberRepository memberRepository;
    private final GroupPurchaseRepository groupPurchaseRepository;
    private final GroupPurchaseImageRepository groupPurchaseImageRepository;

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
}
