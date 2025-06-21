package com.capstone2.capstone2.domain.location.entity;

import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "member_coordinate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberCoordinate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이 좌표를 등록한 회원
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 위도, 경도
    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;
}
