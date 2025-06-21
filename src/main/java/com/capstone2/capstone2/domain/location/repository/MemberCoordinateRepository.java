package com.capstone2.capstone2.domain.location.repository;

import com.capstone2.capstone2.domain.location.entity.MemberCoordinate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCoordinateRepository extends JpaRepository<MemberCoordinate, Long> {
}