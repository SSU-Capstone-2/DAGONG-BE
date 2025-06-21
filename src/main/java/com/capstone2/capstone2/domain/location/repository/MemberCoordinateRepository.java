package com.capstone2.capstone2.domain.location.repository;

import com.capstone2.capstone2.domain.location.entity.MemberCoordinate;
import com.capstone2.capstone2.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberCoordinateRepository extends JpaRepository<MemberCoordinate, Long> {
    List<MemberCoordinate> findAllByMemberOrderByCreatedAt(Member member);

}