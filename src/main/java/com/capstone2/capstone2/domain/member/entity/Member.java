package com.capstone2.capstone2.domain.member.entity;

import com.capstone2.capstone2.domain.chat.entity.ChatMessage;
import com.capstone2.capstone2.domain.groupPurchase.entity.GroupPurchase;
import com.capstone2.capstone2.domain.groupPurchase.entity.Participation;
import com.capstone2.capstone2.domain.model.entity.BaseEntity;
import com.capstone2.capstone2.domain.notification.entity.Notification;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 100)
    private String nickname;

    @Column(nullable = false)
    private Long kakaoId;

    @Column(name = "main_category", length = 50)
    private String mainCategory;      // 대분류

    @Column(name = "sub_category", length = 50)
    private String subCategory;       // 중분류

    private String profile_url;

    private String category;

    @Builder.Default
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupPurchase> groupPurchases = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participation> participations = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberItemLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> sentMessages = new ArrayList<>();


}
