package com.capstone2.capstone2.domain.location.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MemberCoordinatesDTO {
    private Long memberId;
    private List<CoordinateDTO> coordinates;
}