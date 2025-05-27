package com.capstone2.capstone2.domain.review.entity;

import com.capstone2.capstone2.domain.groupPurchase.entity.GroupPurchase;
import com.capstone2.capstone2.domain.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300, nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupPurchase_id", nullable = false)
    private GroupPurchase groupPurchase;

}
