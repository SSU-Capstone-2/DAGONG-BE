package com.capstone2.capstone2.domain.location.service;

// LocationService.java
import com.capstone2.capstone2.domain.kakaoMap.KakaoMapClient;
import com.capstone2.capstone2.domain.location.dto.CityDistrictResponseDTO;
import com.capstone2.capstone2.domain.location.dto.CityOnlyResponseDTO;
import com.capstone2.capstone2.domain.location.dto.LocationRequestDTO;
import com.capstone2.capstone2.domain.location.dto.LocationResponseDTO;
import com.capstone2.capstone2.domain.location.entity.City;
import com.capstone2.capstone2.domain.location.entity.District;
import com.capstone2.capstone2.domain.location.entity.MemberCoordinate;
import com.capstone2.capstone2.domain.location.entity.Town;
import com.capstone2.capstone2.domain.location.repository.CityRepository;
import com.capstone2.capstone2.domain.location.repository.DistrictRepository;
import com.capstone2.capstone2.domain.location.repository.MemberCoordinateRepository;
import com.capstone2.capstone2.domain.location.repository.TownRepository;
import com.capstone2.capstone2.domain.kakaoMap.KakaoMapClient;
import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.member.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final KakaoMapClient kakaoMapClient;
    private final CityRepository cityRepo;
    private final DistrictRepository districtRepo;
    private final TownRepository townRepo;
    private final MemberRepository memberRepository;
    private final MemberCoordinateRepository memberCoordinateRepository;

    @Transactional
    public LocationResponseDTO authenticateLocation(Long memberId, double lat, double lon) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다. id=" + memberId));

        // 위도/경도 저장
        MemberCoordinate coord = MemberCoordinate.builder()
                .member(member)
                .latitude(lat)
                .longitude(lon)
                .build();
        memberCoordinateRepository.save(coord);

        // 카카오 API 호출해서 "시도 구군 읍면동" 얻기
        String fullAddr = kakaoMapClient.reverseGeocode(lat, lon);
        String[] parts = fullAddr.split(" ");

        String cityName = parts[0];
        String districtName = parts[1];
        String townName = parts[2];

        City city = cityRepo.findByNameAndMember(cityName, member)
                .orElseGet(() -> cityRepo.save(
                        City.builder()
                                .name(cityName)
                                .member(member)
                                .build()
                ));

        District district = districtRepo.findByNameAndCity(districtName, city)
                .orElseGet(() -> districtRepo.save(
                        District.builder()
                                .name(districtName)
                                .city(city)
                                .build()
                ));

        long savedCount = townRepo.countByDistrict_City_Member(member);
        if (savedCount >= 2) {
            throw new IllegalStateException("주소는 최대 2개까지만 저장할 수 있습니다.");
        }

        // 이미 저장된 동인지 확인
        if (townRepo.findByNameAndDistrict(townName, district).isPresent()) {
            // 중복 입력도 예외 처리
            throw new IllegalStateException("이미 저장된 주소입니다.");
        }

        // 2개 미만·중복 아님 → 저장

        Town town = townRepo.findByNameAndDistrict(parts[2], district)
                .orElseGet(() -> townRepo.save(Town.builder()
                        .name(parts[2])
                        .district(district)
                        .build()));

        // currentTown 필드 갱신
        member.setCurrentTown(town);
        memberRepository.save(member);

        return new LocationResponseDTO(
                member.getId(),
                town.getId(),
                city.getName(),
                district.getName(),
                town.getName()
        );

    }

    /** ③ 현재 주소 조회 메서드 */
    @Transactional(readOnly = true)
    public LocationResponseDTO getCurrentLocation(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다. id=" + memberId));

        Town town = member.getCurrentTown();
        if (town == null) {
            throw new IllegalStateException("현재 설정된 주소가 없습니다.");
        }
        District district = town.getDistrict();
        City city = district.getCity();

        return new LocationResponseDTO(
                member.getId(),
                town.getId(),
                city.getName(),
                district.getName(),
                town.getName()
        );
    }

    // 현재 주소 변경
    @Transactional
    public LocationResponseDTO changeCurrentTown(Long memberId, Long townId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다. id=" + memberId));

        Town town = townRepo.findById(townId)
                .orElseThrow(() -> new NoSuchElementException("주소가 없습니다. townId=" + townId));

        // 권한 검증: town이 member 소유인지
        City city = town.getDistrict().getCity();
        if (!city.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("주소 변경 권한이 없습니다.");
        }

        // currentTown만 변경
        member.setCurrentTown(town);
        memberRepository.save(member);

        District district = town.getDistrict();
        return new LocationResponseDTO(
                member.getId(),
                town.getId(),
                city.getName(),
                district.getName(),
                town.getName()
        );
    }

    @Transactional(readOnly = true)
    public List<LocationResponseDTO> getMemberLocations(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다. id=" + memberId));

        return townRepo.findAllByDistrict_City_Member(member).stream()
                .map(town -> {
                    District d = town.getDistrict();
                    City c = d.getCity();
                    return new LocationResponseDTO(
                            member.getId(),
                            town.getId(),
                            c.getName(),
                            d.getName(),
                            town.getName()
                    );
                })
                .collect(Collectors.toList());
    }
    @Transactional
    public Long deleteLocation(Long memberId, Long townId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다. id=" + memberId));

        Town town = townRepo.findById(townId)
                .orElseThrow(() -> new NoSuchElementException("삭제할 주소가 없습니다. townId=" + townId));

        // 권한 검증
        City city = town.getDistrict().getCity();
        if (!city.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("삭제할 권한이 없습니다.");
        }

        // 1) currentTown 이 삭제 대상이면 해제 또는 대체
        if (member.getCurrentTown() != null
                && member.getCurrentTown().getId().equals(townId)) {

            // (A) 다른 저장된 주소가 있으면 그 중 하나를 currentTown 으로 설정
            List<Town> otherTowns = townRepo.findAllByDistrict_City_Member(member)
                    .stream().filter(t -> !t.getId().equals(townId))
                    .collect(Collectors.toList());

            if (!otherTowns.isEmpty()) {
                member.setCurrentTown(otherTowns.get(0));
            } else {
                // (B) 남은 주소가 없으면 currentTown 을 null 로
                member.setCurrentTown(null);
            }
            memberRepository.save(member);
        }

        // 2) Town 삭제
        townRepo.delete(town);

        // 3) 연관 District/City 삭제 로직 (기존처럼)
        District district = town.getDistrict();
        if (townRepo.countByDistrict(district) == 0) {
            districtRepo.delete(district);
            if (districtRepo.countByCity(city) == 0) {
                cityRepo.delete(city);
            }
        }

        return member.getId();
    }
//    @Transactional
//    public Long deleteLocation(Long memberId, Long townId) {
//        // 1) 멤버 존재 확인
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다. id=" + memberId));
//
//        // 2) 삭제 대상 Town 조회
//        Town town = townRepo.findById(townId)
//                .orElseThrow(() -> new NoSuchElementException("삭제할 주소가 없습니다. townId=" + townId));
//
//        // 3) 멤버-주소 연관성 검증
//        City city = town.getDistrict().getCity();
//        if (!city.getMember().getId().equals(member.getId())) {
//            throw new IllegalStateException("해당 주소를 삭제할 권한이 없습니다.");
//        }
//
//        District district = town.getDistrict();
//
//        // 1) Town 삭제
//        townRepo.delete(town);
//
//        // 2) 남은 Town이 0개면 District 삭제
//        if (townRepo.countByDistrict(district) == 0) {
//            districtRepo.delete(district);
//
//            // 3) 남은 District가 0개면 City 삭제
//            if (districtRepo.countByCity(city) == 0) {
//                cityRepo.delete(city);
//            }
//        }
//
//        return member.getId();
//    }

    @Transactional(readOnly = true)
    public CityOnlyResponseDTO getCityOnly(Long memberId, Long townId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다. id=" + memberId));

        Town town = townRepo.findById(townId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 주소(town)입니다. townId=" + townId));

        // 권한 확인
        City city = town.getDistrict().getCity();
        if (!city.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("조회 권한이 없습니다.");
        }

        return new CityOnlyResponseDTO(
                member.getId(),
                town.getId(),
                city.getName()
        );
    }

    @Transactional(readOnly = true)
    public CityDistrictResponseDTO getCityAndDistrict(Long memberId, Long townId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다. id=" + memberId));

        Town town = townRepo.findById(townId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 주소(town)입니다. townId=" + townId));

        // 권한 확인
        District district = town.getDistrict();
        City city = district.getCity();
        if (!city.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("조회 권한이 없습니다.");
        }

        return new CityDistrictResponseDTO(
                member.getId(),
                town.getId(),
                city.getName(),
                district.getName()
        );
    }

    // 기존 LocationResponseDTO getCityDistrictTown(Long ...) 와 동일하게 사용
    @Transactional(readOnly = true)
    public LocationResponseDTO getFullLocation(Long memberId, Long townId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다. id=" + memberId));

        Town town = townRepo.findById(townId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 주소(town)입니다. townId=" + townId));

        // 권한 확인
        District district = town.getDistrict();
        City city = district.getCity();
        if (!city.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("조회 권한이 없습니다.");
        }

        return new LocationResponseDTO(
                member.getId(),
                town.getId(),
                city.getName(),
                district.getName(),
                town.getName()
        );

    }

    
}

