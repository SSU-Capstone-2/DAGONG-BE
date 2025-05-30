package com.capstone2.capstone2.domain.groupPurchase.service;

import com.capstone2.capstone2.domain.groupPurchase.dto.GroupPurchaseRequest;
import com.capstone2.capstone2.domain.groupPurchase.entity.GroupPurchase;

public interface GroupPurchaseService {
    GroupPurchase createGroupPurchase(Long memberId, GroupPurchaseRequest.GroupPurchaseCreateDTO request);
}
