package com.capstone2.capstone2.global.repository;

import com.capstone2.capstone2.global.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
