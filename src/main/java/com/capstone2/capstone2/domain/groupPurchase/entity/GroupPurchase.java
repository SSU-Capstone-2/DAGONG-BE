package com.capstone2.capstone2.domain.groupPurchase.entity;


import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.model.enums.Status;
import com.capstone2.capstone2.domain.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GroupPurchase extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 300)
    private String content;

    @Column
    private String place;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    // 공구 상품 이름
    @Column(nullable = false)
    private String name;

    // 공구 상품 수량
    @Column(nullable = false)
    private Integer quantity;

    // 공구 상품 가격
    @Column(nullable = false)
    private Integer price;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private Member writer;

    // 공구 대분류
    private String category1;

    // 공구 소분류
    private String category2;

    private Integer view;

    private Integer like;

    // 마감 예측 시간
    private LocalDateTime deadline;
}
