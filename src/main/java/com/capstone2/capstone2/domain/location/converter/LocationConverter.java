package com.capstone2.capstone2.domain.location.converter;


import com.capstone2.capstone2.domain.location.dto.LocationResponseDTO;
import com.capstone2.capstone2.domain.location.entity.City;
import com.capstone2.capstone2.domain.location.entity.District;
import com.capstone2.capstone2.domain.location.entity.Town;
import com.capstone2.capstone2.domain.member.entity.Member;

public class LocationConverter {

    public static LocationResponseDTO toResponseDTO(Member member, City city, District district, Town town) {
        return new LocationResponseDTO(
                member.getId(),
                town.getId(),
                city.getName(),
                district.getName(),
                town.getName()
        );
    }
}
