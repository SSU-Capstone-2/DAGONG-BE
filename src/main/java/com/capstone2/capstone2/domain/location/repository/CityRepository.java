package com.capstone2.capstone2.domain.location.repository;

import com.capstone2.capstone2.domain.location.entity.City;
import com.capstone2.capstone2.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByNameAndMember(String name, Member member);
}
