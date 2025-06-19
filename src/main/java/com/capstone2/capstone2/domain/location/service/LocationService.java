package com.capstone2.capstone2.domain.location.service;

// LocationService.java
import com.capstone2.capstone2.domain.kakaoMap.KakaoMapClient;
import com.capstone2.capstone2.domain.location.dto.LocationRequestDTO;
import com.capstone2.capstone2.domain.location.dto.LocationResponseDTO;
import com.capstone2.capstone2.domain.location.entity.City;
import com.capstone2.capstone2.domain.location.entity.District;
import com.capstone2.capstone2.domain.location.entity.Town;
import com.capstone2.capstone2.domain.location.repository.CityRepository;
import com.capstone2.capstone2.domain.location.repository.DistrictRepository;
import com.capstone2.capstone2.domain.location.repository.TownRepository;
import com.capstone2.capstone2.domain.kakaoMap.KakaoMapClient;
import com.capstone2.capstone2.domain.member.entity.Member;
import com.capstone2.capstone2.domain.member.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final KakaoMapClient kakaoMapClient;
    private final CityRepository cityRepo;
    private final DistrictRepository districtRepo;
    private final TownRepository townRepo;
    private final MemberRepository memberRepository;

    @Transactional
    public LocationResponseDTO authenticateLocation(Long memberId, double lat, double lon) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다. id=" + memberId));

        // 1) 카카오 API 호출해서 "시도 구군 읍면동" 얻기
        String fullAddr = kakaoMapClient.reverseGeocode(lat, lon);
        String[] parts = fullAddr.split(" ");

        String cityName     = parts[0];
        String districtName = parts[1];
        String townName     = parts[2];

        // 2) DB 저장/조회
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

        // 5) Town 조회·저장
//        Town town = townRepo.findByNameAndDistrict(townName, district)
//                .orElseGet(() -> townRepo.save(
//                        Town.builder()
//                                .name(townName)
//                                .district(district)
//                                .build()
//                ));
        Optional<Town> existingTown = townRepo.findByNameAndDistrict(townName, district);

        if (existingTown.isEmpty()) {
            // 2) 멤버가 저장한 Town(동) 개수 조회
            long savedCount = townRepo.countByDistrict_City_Member(member);

            // 3) 이미 2개 이상이면 저장 차단
            if (savedCount >= 2) {
                throw new IllegalStateException("주소는 최대 2개까지만 저장할 수 있습니다.");
            }
            // 4) 2개 미만이면 새로 저장
            Town newTown = Town.builder()
                    .name(townName)
                    .district(district)
                    .build();
            existingTown = Optional.of(townRepo.save(newTown));
        }
        Town town = existingTown.get();


        // 3) DTO 반환
        return new LocationResponseDTO(
                member.getId(),
                city.getName(),
                district.getName(),
                town.getName()
        );
    }
}

