package com.capstone2.capstone2.domain.groupPurchase.service;

import com.capstone2.capstone2.domain.groupPurchase.dto.GroupPurchaseRequest;
import com.capstone2.capstone2.domain.groupPurchase.dto.GroupPurchaseResponse;
import com.capstone2.capstone2.domain.groupPurchase.entity.GroupPurchase;
import org.springframework.data.domain.Page;

public interface GroupPurchaseService {
    GroupPurchase createGroupPurchase(Long memberId, GroupPurchaseRequest.GroupPurchaseCreateDTO request);
    Page<GroupPurchaseResponse.GroupPurchaseListDTO> getAllPurchases(int page, int size);
    GroupPurchaseResponse.GroupPurchaseDetailDTO getGroupPurchaseDetail(Long purchaseId);
    GroupPurchase updateGroupPurchase(Long groupPurchaseId, GroupPurchaseRequest.GroupPurchaseUpdateDTO request);
    void deleteGroupPurchase(Long groupPurchaseId);
    Page<GroupPurchaseResponse.GroupPurchaseListDTO> getPopularGroupPurchases(String sort, int page ,int size);
    Page<GroupPurchaseResponse.GroupPurchaseListDTO> getGroupPurchasesByCategory(String category1, String category2, int page, int size);
    GroupPurchase participateGroupPurchase(Long groupPurchaseId, Long memberId);
    GroupPurchase cancelGroupPurchaseParticipate(Long groupPurchaseId, Long memberId);


    Page<GroupPurchaseResponse.GroupPurchaseListDTO> searchGroupPurchases(
            Long memberId,
            String itemName,
            String category,    // nullable
            String sort,        // views, oldest, likes, latest
            int page,
            int size
    );
}
