package com.capstone2.capstone2.domain.groupPurchase.converter;

import com.capstone2.capstone2.domain.groupPurchase.dto.GroupPurchaseRequest;
import com.capstone2.capstone2.domain.groupPurchase.dto.GroupPurchaseResponse;
import com.capstone2.capstone2.domain.groupPurchase.entity.GroupPurchase;
import com.capstone2.capstone2.domain.groupPurchase.entity.GroupPurchaseImage;
import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.model.enums.Status;

public class GroupPurchaseConverter {
    public static GroupPurchase toGroupPurchase(Member member, GroupPurchaseRequest.GroupPurchaseCreateDTO request) {
        return GroupPurchase.builder()
                .title(request.getTitle())
                .name(request.getName())
                .category1(request.getCategory1())
                .category2(request.getCategory2())
                .content(request.getContent())
                .quantity(request.getQuantity())
                .participants(request.getParticipants())
                .price(request.getPrice())
                .views(0)
                .likes(0)
                .writer(member)
                .status(Status.PROCEEDING)
                .build();
    }

    public static GroupPurchaseImage toGroupPurchaseImage(GroupPurchase groupPurchase, String imageUrl) {
        return GroupPurchaseImage.builder()
                .groupPurchase(groupPurchase)
                .imageUrl(imageUrl)
                .build();
    }


    public static GroupPurchaseResponse.GroupPurchaseIdDTO toGroupPurchaseIdDTO(GroupPurchase groupPurchase) {
        return GroupPurchaseResponse.GroupPurchaseIdDTO.builder()
                .groupPurchaseId(groupPurchase.getId())
                .build();
    }

    public static GroupPurchaseResponse.GroupPurchaseListDTO toGroupPurchaseListDTO(GroupPurchase groupPurchase) {
        return GroupPurchaseResponse.GroupPurchaseListDTO.builder()
                .id(groupPurchase.getId())
                .title(groupPurchase.getTitle())
                .price(groupPurchase.getPrice())
                .place(groupPurchase.getPlace())
                .participants(groupPurchase.getParticipants())
                .deadline(groupPurchase.getDeadline())
                .status(groupPurchase.getStatus())
                .views(groupPurchase.getViews())
                .likes(groupPurchase.getLikes())
                .build();
    }
}
