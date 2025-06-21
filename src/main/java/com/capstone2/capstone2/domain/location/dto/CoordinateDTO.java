package com.capstone2.capstone2.domain.location.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class CoordinateDTO {
    private double    latitude;
    private double    longitude;
}
