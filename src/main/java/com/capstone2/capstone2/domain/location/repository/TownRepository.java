package com.capstone2.capstone2.domain.location.repository;

import com.capstone2.capstone2.domain.location.entity.District;
import com.capstone2.capstone2.domain.location.entity.Town;
import com.capstone2.capstone2.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TownRepository extends JpaRepository<Town, Long> {
    Optional<Town> findByNameAndDistrict(String name, District district);

    long countByDistrict_City_Member(Member member);

    List<Town> findAllByDistrict_City_Member(Member member);

}
