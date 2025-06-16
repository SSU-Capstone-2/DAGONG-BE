package com.capstone2.capstone2.domain.category.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "main_category")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MainCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;
}
