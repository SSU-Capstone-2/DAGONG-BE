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
}
