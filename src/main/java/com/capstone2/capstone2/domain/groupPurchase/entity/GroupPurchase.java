package com.capstone2.capstone2.domain.groupPurchase.entity;

import com.capstone2.capstone2.domain.chat.entity.ChatRoom;
import com.capstone2.capstone2.domain.groupPurchase.dto.GroupPurchaseRequest;
import com.capstone2.capstone2.domain.groupPurchase.handler.ParticipationHandler;
import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.model.enums.Status;
import com.capstone2.capstone2.domain.model.entity.BaseEntity;
import com.capstone2.capstone2.global.error.code.status.ErrorStatus;
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

    // 설정된 최대 참여 인원 수
    private Integer maxParticipants;

    // 현재 참여 인원 수
    private Integer currentParticipants;

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

    @Column(name = "current_district_id", nullable = false)
    private Long currentDistrictId;

    @Builder.Default
    @OneToMany(mappedBy = "groupPurchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupPurchaseImage> groupPurchaseImages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "groupPurchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participation> participations = new ArrayList<>();

    @OneToOne(mappedBy = "groupPurchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "current_district_id",      // FK 컬럼
            referencedColumnName = "id",       // District 엔티티 PK
            insertable = false,
            updatable = false
    )

    // 공동 구매 정보 수정
    public void updateGroupPurchase(GroupPurchaseRequest.GroupPurchaseUpdateDTO request) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.place = request.getPlace();
        this.name = request.getName();
        this.quantity = request.getQuantity();
        this.maxParticipants = request.getMaxParticipants();
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

    public void addParticipation(Participation participation) {
        if (this.status != Status.ACTIVE) {
            throw new ParticipationHandler(ErrorStatus.PARTICIPATION_NOT_ACTIVE);
        }

        if (this.currentParticipants >= this.maxParticipants) {
            throw new ParticipationHandler(ErrorStatus.PARTICIPATION_UP_TO_MAX);
        }

        this.participations.add(participation);
        this.currentParticipants += 1;

        if (this.currentParticipants.equals(this.maxParticipants)) {
            this.status = Status.COMPLETE;
        }
    }

    public void decreaseParticipants() {
        if (this.currentParticipants > 0) {
            this.currentParticipants--;
        }
    }

    public void activeIfWasProceeding() {
        if (this.status == Status.COMPLETE) {
            this.status = Status.ACTIVE;
        }
    }

    public void increaseLikes() {
        if (this.likes == null) {
            this.likes = 0;
        }
        this.likes++;
    }

    public void decreaseLikes() {
        if (this.likes == null || this.likes <= 0) {
            this.likes = 0;
        } else {
            this.likes--;
        }
    }

}
