package com.capstone2.capstone2.domain.location.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponseDTO {
    private String city;
    private String district;
    private String town;
}