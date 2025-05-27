package com.capstone2.capstone2.domain.member.entity;

import com.capstone2.capstone2.domain.groupPurchase.entity.GroupPurchase;
import com.capstone2.capstone2.domain.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "groupPurchase_id"}))
public class MemberItemLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupPurchase_id", nullable = false)
    private GroupPurchase groupPurchase;
}