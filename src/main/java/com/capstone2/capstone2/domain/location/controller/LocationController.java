package com.capstone2.capstone2.domain.location.controller;

// LocationController.java
import com.capstone2.capstone2.domain.location.dto.LocationRequestDTO;
import com.capstone2.capstone2.domain.location.dto.LocationResponseDTO;
import com.capstone2.capstone2.domain.location.service.LocationService;
import com.capstone2.capstone2.global.common.response.ApiResponse;
import com.capstone2.capstone2.global.error.code.status.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public ApiResponse<LocationResponseDTO> authenticateLocation(
            @RequestBody LocationRequestDTO request) {

        LocationResponseDTO dto = locationService.authenticateLocation(
                request.getMemberId(),
                request.getLatitude(),
                request.getLongitude()
        );
        return ApiResponse.onSuccess(SuccessStatus.LOCATION_CERTIFY_OK,dto);
    }

    @GetMapping("/{memberId}")
    public ApiResponse<List<LocationResponseDTO>> getMemberLocations(
            @PathVariable Long memberId) {

        List<LocationResponseDTO> list = locationService.getMemberLocations(memberId);
        return ApiResponse.onSuccess(SuccessStatus.LOCATION_GET_OK, list);
    }


    @DeleteMapping("/{memberId}/{townId}")
    public ApiResponse<Long> deleteLocation(
            @PathVariable Long memberId,
            @PathVariable Long townId) {
        Long deletedMemberId = locationService.deleteLocation(memberId, townId);
        return ApiResponse.onSuccess(SuccessStatus.LOCATION_DELETE_OK, deletedMemberId);
    }
}
