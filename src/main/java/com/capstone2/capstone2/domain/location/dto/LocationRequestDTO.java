package com.capstone2.capstone2.domain.location.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequestDTO {
    private Long memberId;

    private double latitude;
    private double longitude;
}