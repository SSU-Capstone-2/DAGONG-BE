package com.capstone2.capstone2.domain.location.repository;

import com.capstone2.capstone2.domain.location.entity.District;
import com.capstone2.capstone2.domain.location.entity.Town;
import com.capstone2.capstone2.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TownRepository extends JpaRepository<Town, Long> {
    Optional<Town> findByNameAndDistrict(String name, District district);

    long countByDistrict_City_Member(Member member);

    List<Town> findAllByDistrict_City_Member(Member member);
    long countByDistrict(District district);

    // 멤버가 가진 모든 Town의 isCurrent=false 로 일괄 업데이트
    @Modifying
    @Query("UPDATE Town t SET t.isCurrent = false WHERE t.district.city.member = :member")
    void clearCurrentFlags(@Param("member") Member member);

}
