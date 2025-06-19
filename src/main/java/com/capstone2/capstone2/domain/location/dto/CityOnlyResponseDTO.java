package com.capstone2.capstone2.domain.location.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CityOnlyResponseDTO {
    private Long   memberId;
    private Long   townId;
    private String city;
}
