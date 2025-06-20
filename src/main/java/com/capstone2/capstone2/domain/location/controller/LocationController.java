package com.capstone2.capstone2.domain.location.controller;

// LocationController.java
import com.capstone2.capstone2.domain.location.dto.CityDistrictResponseDTO;
import com.capstone2.capstone2.domain.location.dto.CityOnlyResponseDTO;
import com.capstone2.capstone2.domain.location.dto.LocationRequestDTO;
import com.capstone2.capstone2.domain.location.dto.LocationResponseDTO;
import com.capstone2.capstone2.domain.location.service.LocationService;
import com.capstone2.capstone2.global.common.response.ApiResponse;
import com.capstone2.capstone2.global.error.code.status.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
@Tag(name = "주소 API", description = "주소 관련 API")

public class LocationController {

    private final LocationService locationService;

    @Operation(summary = "주소 등록", description = "회원의 위도 경도를 받아 주소를 저장합니다.(최대 2개까지 저장 가능, 중복 시 에러)")
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

    @Operation(summary = "현재 주소 조회", description = "현재 회원의 주소를 반환합니다.")
    @GetMapping("/{memberId}/current")
    public ApiResponse<LocationResponseDTO> getCurrentLocation(
            @PathVariable Long memberId) {

        LocationResponseDTO dto = locationService.getCurrentLocation(memberId);
        return ApiResponse.onSuccess(SuccessStatus.LOCATION_CURRENT_CERTIFY_OK,dto);
    }

    /**
     * 저장된 주소(최대 2개) 중 하나를
     * “현재 주소”로 변경
     */
    @Operation(summary = "현재 주소 변경", description = "현재 회원의 주소를 변경합니다.")
    @PutMapping("/{memberId}/current/{townId}")
    public ApiResponse<LocationResponseDTO> switchCurrentTown(
            @PathVariable Long memberId,
            @PathVariable Long townId) {

        LocationResponseDTO dto = locationService.changeCurrentTown(memberId, townId);
        return ApiResponse.onSuccess(SuccessStatus.LOCATION_CURRENT_CHANGE_OK,dto);
    }

    @Operation(summary = "주소 조회", description = "회원의 주소들을 조회합니다.")
    @GetMapping("/{memberId}")
    public ApiResponse<List<LocationResponseDTO>> getMemberLocations(
            @PathVariable Long memberId) {

        List<LocationResponseDTO> list = locationService.getMemberLocations(memberId);
        return ApiResponse.onSuccess(SuccessStatus.LOCATION_GET_OK, list);
    }


    @Operation(summary = "주소 삭제", description = "회원 id와 town id를 받아 주소를 삭제합니다.")
    @DeleteMapping("/{memberId}/{townId}")
    public ApiResponse<Long> deleteLocation(
            @PathVariable Long memberId,
            @PathVariable Long townId) {
        Long deletedMemberId = locationService.deleteLocation(memberId, townId);
        return ApiResponse.onSuccess(SuccessStatus.LOCATION_DELETE_OK, deletedMemberId);
    }

    @Operation(summary = "주소 조회(시까지 조회)", description = "회원 id와 town id를 받아 주소의 시까지 조회합니다.")
    @GetMapping("/{memberId}/{townId}/city")
    public ApiResponse<CityOnlyResponseDTO> getCityOnly(
            @PathVariable Long memberId,
            @PathVariable Long townId) {

        CityOnlyResponseDTO dto = locationService.getCityOnly(memberId, townId);
        return ApiResponse.onSuccess(SuccessStatus.LOCATION_GET_OK, dto);
    }

    @Operation(summary = "주소 조회(시, 구까지 조회)", description = "회원 id와 town id를 받아 주소의 시, 구까지 조회합니다.")
    @GetMapping("/{memberId}/{townId}/city-district")
    public ApiResponse<CityDistrictResponseDTO> getCityAndDistrict(
            @PathVariable Long memberId,
            @PathVariable Long townId) {

        CityDistrictResponseDTO dto = locationService.getCityAndDistrict(memberId, townId);
        return ApiResponse.onSuccess(SuccessStatus.LOCATION_GET_OK, dto);
    }

    @Operation(summary = "주소 조회(시, 구, 동까지 조회)", description = "회원 id와 town id를 받아 주소의 시, 구, 동까지 조회합니다.")
    @GetMapping("/{memberId}/{townId}")
    public ApiResponse<LocationResponseDTO> getFullLocation(
            @PathVariable Long memberId,
            @PathVariable Long townId) {

        LocationResponseDTO dto = locationService.getFullLocation(memberId, townId);
        return ApiResponse.onSuccess(SuccessStatus.LOCATION_GET_OK, dto);
    }


}
