package com.capstone2.capstone2.domain.groupPurchase.entity;

import com.capstone2.capstone2.domain.groupPurchase.dto.GroupPurchaseRequest;
import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.model.enums.Status;
import com.capstone2.capstone2.domain.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group_purchase")
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

    // 참여 인원 수
    private Integer participants;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private Member writer;

    // 공구 대분류
    private String category1;

    // 공구 소분류
    private String category2;

    private Integer views;

    private Integer likes;

    // 마감 예측 시간
    private LocalDateTime deadline;

    @Builder.Default
    @OneToMany(mappedBy = "groupPurchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupPurchaseImage> groupPurchaseImages = new ArrayList<>();

    // 공동 구매 정보 수정
    public void updateGroupPurchase(GroupPurchaseRequest.GroupPurchaseUpdateDTO request) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.place = request.getPlace();
        this.name = request.getName();
        this.quantity = request.getQuantity();
        this.participants = request.getParticipants();
        this.category1 = request.getCategory1();
        this.category2 = request.getCategory2();
        this.deadline = request.getDeadline();

        this.groupPurchaseImages.clear();
        if (request.getImageUrls() != null) {
            for (String imageUrl: request.getImageUrls()) {
                GroupPurchaseImage image = new GroupPurchaseImage(this, imageUrl);
                this.groupPurchaseImages.add(image);
            }
        }
    }

    public void increaseViews() {
        this.views += 1;
    }
}
